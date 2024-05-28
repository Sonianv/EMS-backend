package com.ems.service;

import com.ems.config.JwtService;
import com.ems.dto.EmployeeDto;
import com.ems.dto.auth.AuthRequest;
import com.ems.dto.auth.AuthResponse;
import com.ems.error.AuthException;
import com.ems.error.InvalidRequestBodyException;
import com.ems.error.ResourceNotFoundException;
import com.ems.model.Employee;
import com.ems.model.Token;
import com.ems.repository.EmployeeRepository;
import com.ems.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.ems.converter.EmployeeConverter.convertToEmployee;
import static com.ems.converter.EmployeeConverter.convertToEmployeeDto;
import static com.ems.model.Token.TokenType.BEARER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public EmployeeDto addNewEmployee(EmployeeDto employeeDto) {
        if (employeeRepository.findByEmail(employeeDto.getEmail()).isPresent()) {
            throw new InvalidRequestBodyException("An employee with the specified email already exists");
        }
        Employee employee = convertToEmployee(employeeDto, roleService, passwordEncoder);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToEmployeeDto(savedEmployee);
    }

    public String changePassword(String password, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AuthException("Invalid token!");
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt).orElseThrow(() -> new AuthException("Invalid token!"));
        if (storedToken.isExpired() || storedToken.isRevoked()) {
            throw new AuthException("Cannot change password!");
        }
        Employee employee = storedToken.getEmployee();
        employee.setPassword(passwordEncoder.encode(password));
        employeeRepository.save(employee);
        return "Password changed successfully.";
    }

    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );
        var employee = employeeRepository.findByEmail(authRequest.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Cannot find employee with email " + authRequest.getEmail()));
        var jwtToken = jwtService.generateToken(employee);
        revokeAllEmployeeTokens(employee);
        var token = Token.builder()
                .employee(employee)
                .token(jwtToken)
                .tokenType(BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
        return AuthResponse.builder()
                .token(jwtToken)
                .id(employee.getId())
                .email(employee.getEmail())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .roleName(employee.getRole().getName().name())
                .build();
    }

    private void revokeAllEmployeeTokens(Employee employee) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(employee.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
