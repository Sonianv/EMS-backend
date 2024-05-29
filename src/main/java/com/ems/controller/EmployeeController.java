package com.ems.controller;

import com.ems.dto.ReportDateDto;
import com.ems.service.EmployeeService;
import com.ems.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Month;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/rest/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ReportService reportService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @PostMapping("/report")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<InputStreamResource> generateReport(@Valid @RequestBody ReportDateDto reportDateDto) throws FileNotFoundException {
        File report = reportService.generateReport(reportDateDto);
        String reportName = report.getName();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + reportName);
        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(report));

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(inputStreamResource);
    }

    @GetMapping("/summary/{year}/{month}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getMonthSummary(@PathVariable("year") int year, @PathVariable("month") Month month) {
        return new ResponseEntity<>(employeeService.getMonthSummary(year, month), HttpStatus.OK);
    }
}
