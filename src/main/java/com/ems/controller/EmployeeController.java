package com.ems.controller;

import com.ems.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/rest/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

}
