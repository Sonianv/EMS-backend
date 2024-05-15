package com.ems.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private Long id;
    @NotBlank(message = "First name must be specified")
    private String firstName;
    @NotBlank(message = "Last name must be specified")
    private String lastName;
    @NotNull(message = "Role must be specified")
    private Long roleId;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Start date must be specified")
    private LocalDate startDate;
    private LocalDate endDate;
    private int program;
    @NotNull(message = "Password cannot be blank")
    private String password;
}
