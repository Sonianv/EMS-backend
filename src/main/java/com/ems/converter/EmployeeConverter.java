package com.ems.converter;

import com.ems.dto.EmployeeDto;
import com.ems.model.Employee;
import com.ems.service.RoleService;

public class EmployeeConverter {

    public static EmployeeDto convertToEmployeeDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getRole().getId(),
                employee.getEmail(),
                employee.getStartDate(),
                employee.getEndDate(),
                employee.getProgram()
        );
    }

    public static Employee convertToEmployee(EmployeeDto employeeDto, RoleService roleService) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setRole(roleService.findById(employeeDto.getRoleId()));
        employee.setEmail(employeeDto.getEmail());
        employee.setStartDate(employeeDto.getStartDate());
        employee.setEndDate(employeeDto.getEndDate());
        employee.setProgram(employeeDto.getProgram());
        return employee;
    }

}
