package com.ems.controller;

import com.ems.dto.EmployeeVacationDto;
import com.ems.service.EmployeeVacationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/rest/employee_vacations")
public class EmployeeVacationController {

    private final EmployeeVacationService employeeVacationService;

    @PostMapping("/new")
    public ResponseEntity<?> addEmployeeWorkDay(@Valid @RequestBody EmployeeVacationDto employeeVacationDto) {
        return new ResponseEntity<>(employeeVacationService.addEmployeeVacation(employeeVacationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployeeWorkDay(@PathVariable("id") Long id, @Valid @RequestBody EmployeeVacationDto employeeVacationDto) {
        return new ResponseEntity<>(employeeVacationService.updateEmployeeVacation(id, employeeVacationDto), HttpStatus.CREATED);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatusEmployeeWorkDay(@PathVariable("id") Long id, @Valid @RequestBody EmployeeVacationDto employeeVacationDto) {
        return new ResponseEntity<>(employeeVacationService.updateStatusOfEmployeeVacation(id, employeeVacationDto), HttpStatus.CREATED);
    }

}
