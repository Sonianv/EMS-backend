package com.ems.service;

import com.ems.config.JwtService;
import com.ems.converter.EmployeeConverter;
import com.ems.dto.EmployeeDto;
import com.ems.dto.auth.AuthRequest;
import com.ems.dto.auth.AuthResponse;
import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(EmployeeDto employeeDto) {
        Employee employee = EmployeeConverter.convertToEmployee(employeeDto, roleService, passwordEncoder);
        employeeRepository.save(employee);
        var jwtToken = jwtService.generateToken(employee);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );
        var employee = employeeRepository.findByEmail(authRequest.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(employee);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
