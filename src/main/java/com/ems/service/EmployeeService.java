package com.ems.service;

import com.ems.converter.EmployeeConverter;
import com.ems.dto.EmployeeDto;
import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final RoleService roleService;

    public EmployeeService(EmployeeRepository employeeRepository, RoleService roleService) {
        this.employeeRepository = employeeRepository;
        this.roleService = roleService;
    }

    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeConverter.convertToEmployee(employeeDto, roleService);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeConverter.convertToEmployeeDto(savedEmployee);
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot find employee with id " + id));
    }
}
