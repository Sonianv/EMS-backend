package com.ems.converter;

import com.ems.dto.EmployeeDto;
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
                .build();
    }

    public static Employee convertToEmployee(EmployeeDto employeeDto, RoleService roleService, PasswordEncoder passwordEncoder) {
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
