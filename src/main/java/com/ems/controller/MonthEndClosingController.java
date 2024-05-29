package com.ems.controller;

import com.ems.dto.MonthEndClosingDto;
import com.ems.service.MonthEndClosingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Month;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/rest/month_end_closing")
public class MonthEndClosingController {

    private final MonthEndClosingService monthEndClosingService;

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addNewMonthEndClosing(@RequestBody MonthEndClosingDto monthEndClosingDto) {
        return new ResponseEntity<>(monthEndClosingService.addNewMonthEndClosing(monthEndClosingDto), HttpStatus.CREATED);
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<?> getMonthEndClosingByYearAndMonth(@PathVariable("year") int year, @PathVariable("month") Month month) {
        return new ResponseEntity<>(monthEndClosingService.getMonthEndClosingByYearAndMonth(year, month), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllMonthEndClosing() {
        return new ResponseEntity<>(monthEndClosingService.getAllMonthEndClosings(), HttpStatus.OK);
    }
}
