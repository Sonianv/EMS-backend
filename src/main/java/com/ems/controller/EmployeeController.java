package com.ems.controller;

import com.ems.dto.ReportDateDto;
import com.ems.service.EmployeeService;
import com.ems.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/rest/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<String> generateReport(@Valid @RequestBody ReportDateDto reportDateDto) {
        reportService.generateReport(reportDateDto);
        return ResponseEntity.ok("Report generated successfully!");
    }
}
