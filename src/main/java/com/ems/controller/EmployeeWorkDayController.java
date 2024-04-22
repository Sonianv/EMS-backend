package com.ems.controller;

import com.ems.dto.EmployeeWorkDayDto;
import com.ems.service.EmployeeWorkDayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rest/workdays")
public class EmployeeWorkDayController {

    private final EmployeeWorkDayService employeeWorkDayService;

    public EmployeeWorkDayController(EmployeeWorkDayService employeeWorkDayService) {
        this.employeeWorkDayService = employeeWorkDayService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> addEmployeeWorkDay(@RequestBody EmployeeWorkDayDto employeeWorkDayDto) {
        return new ResponseEntity<>(employeeWorkDayService.addEmployeeWorkDay(employeeWorkDayDto), HttpStatus.CREATED);
    }
}
