package com.ems.controller;

import com.ems.dto.EmployeeWorkDayDto;
import com.ems.service.EmployeeWorkDayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployeeWorkDay(@PathVariable("id") Long id, @RequestBody EmployeeWorkDayDto employeeWorkDayDto) {
        return new ResponseEntity<>(employeeWorkDayService.updateEmployeeWorkDay(id, employeeWorkDayDto), HttpStatus.CREATED);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<?> getAllEmployeeWorkDaysByEmployeeId(@PathVariable("employeeId") Long id) {
        return new ResponseEntity<>(employeeWorkDayService.getAllEmployeeWorkDays(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeWorkDayById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(employeeWorkDayService.getEmployeeWorkDayDto(id), HttpStatus.OK);
    }
}
