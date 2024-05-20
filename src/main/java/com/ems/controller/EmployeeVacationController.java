package com.ems.controller;

import com.ems.dto.vacation.EmployeeVacationRequest;
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
    public ResponseEntity<?> addEmployeeWorkDay(@Valid @RequestBody EmployeeVacationRequest employeeVacationRequest) {
        return new ResponseEntity<>(employeeVacationService.addEmployeeVacation(employeeVacationRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployeeWorkDay(@PathVariable("id") Long id, @Valid @RequestBody EmployeeVacationRequest employeeVacationRequest) {
        return new ResponseEntity<>(employeeVacationService.updateEmployeeVacation(id, employeeVacationRequest), HttpStatus.CREATED);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatusEmployeeWorkDay(@PathVariable("id") Long id, @RequestBody String statusName) {
        return new ResponseEntity<>(employeeVacationService.updateStatusOfEmployeeVacation(id, statusName), HttpStatus.CREATED);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getAllVacationRequestsByStatus(@PathVariable("status") String status) {
        return new ResponseEntity<>(employeeVacationService.getAllEmployeeVacationsByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<?> updateStatusEmployeeWorkDay(@PathVariable("employeeId") Long employeeId) {
        return new ResponseEntity<>(employeeVacationService.getAllEmployeeVacationsByEmployeeId(employeeId), HttpStatus.OK);
    }

}
