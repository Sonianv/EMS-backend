package com.ems.controller;

import com.ems.dto.vacation.EmployeeVacationRequest;
import com.ems.service.EmployeeVacationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/rest/employee_vacations")
public class EmployeeVacationController {

    private final EmployeeVacationService employeeVacationService;

    @PostMapping("/new")
    public ResponseEntity<?> addEmployeeVacation(@Valid @RequestBody EmployeeVacationRequest employeeVacationRequest) {
        return new ResponseEntity<>(employeeVacationService.addEmployeeVacation(employeeVacationRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployeeVacation(@PathVariable("id") Long id, @Valid @RequestBody EmployeeVacationRequest employeeVacationRequest) {
        return new ResponseEntity<>(employeeVacationService.updateEmployeeVacation(id, employeeVacationRequest), HttpStatus.CREATED);
    }

    @PutMapping("/status/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateStatusEmployeeVacation(@PathVariable("id") Long id, @RequestBody String statusName) {
        return new ResponseEntity<>(employeeVacationService.updateStatusOfEmployeeVacation(id, statusName), HttpStatus.CREATED);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllVacationRequestsByStatus(@PathVariable("status") String status) {
        return new ResponseEntity<>(employeeVacationService.getAllEmployeeVacationsByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<?> getAllVacationRequestsByEmployeeId(@PathVariable("employeeId") Long employeeId) {
        return new ResponseEntity<>(employeeVacationService.getAllEmployeeVacationsByEmployeeId(employeeId), HttpStatus.OK);
    }

}
