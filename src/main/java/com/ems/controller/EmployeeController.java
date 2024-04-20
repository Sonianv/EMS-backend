package com.ems.controller;

import com.ems.dto.EmployeeDto;
import com.ems.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rest/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDto employeeDto) {
        return new ResponseEntity<>(employeeService.addEmployee(employeeDto), HttpStatus.CREATED);
    }
}
