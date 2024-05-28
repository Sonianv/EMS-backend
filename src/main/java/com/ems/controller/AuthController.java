package com.ems.controller;

import com.ems.dto.EmployeeDto;
import com.ems.dto.auth.AuthRequest;
import com.ems.dto.auth.AuthResponse;
import com.ems.service.AuthService;
import com.ems.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/rest/auth")
public class AuthController {

    private final AuthService authService;
    private final LogoutService logoutService;

    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        return new ResponseEntity<>(authService.addNewEmployee(employeeDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return ResponseEntity.ok(logoutService.logout(request));
    }

    @PutMapping("/change_password")
    public ResponseEntity<String> changePassword(@RequestBody String password, HttpServletRequest request) {
        return ResponseEntity.ok(authService.changePassword(password, request));
    }
}
