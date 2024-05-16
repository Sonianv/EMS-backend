package com.ems.converter;

import com.ems.dto.EmployeeDto;
import com.ems.error.InvalidRequestBodyException;
import com.ems.model.Employee;
import com.ems.service.RoleService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EmployeeConverter {

    public static EmployeeDto convertToEmployeeDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .roleId(employee.getRole().getId())
                .email(employee.getEmail())
                .startDate(employee.getStartDate())
                .endDate(employee.getEndDate())
                .program(employee.getProgram())
                .password(employee.getPassword())
                .build();
    }

    public static Employee convertToEmployee(EmployeeDto employeeDto, RoleService roleService, PasswordEncoder passwordEncoder) {
        if (employeeDto.getEndDate() != null && employeeDto.getStartDate().isAfter(employeeDto.getEndDate())) {
            throw new InvalidRequestBodyException("Cannot have end date before start date.");
        } else if (employeeDto.getProgram() == 0) {
            throw new InvalidRequestBodyException("Program must be specified.");
        }
        return Employee.builder()
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .role(roleService.findById(employeeDto.getRoleId()))
                .email(employeeDto.getEmail())
                .startDate(employeeDto.getStartDate())
                .endDate(employeeDto.getEndDate())
                .program(employeeDto.getProgram())
                .password(passwordEncoder.encode(employeeDto.getPassword()))
                .build();
    }

}
