package com.ems.controller;

import com.ems.dto.OfficialHolidayDto;
import com.ems.service.OfficialHolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/rest/officialHolidays")
public class OfficialHolidayController {

    private final OfficialHolidayService officialHolidayService;

    @GetMapping
    public ResponseEntity<?> getAllOfficialHolidays() {
        return new ResponseEntity<>(officialHolidayService.getAllOfficialHolidays(), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<?> addNewOfficialHoliday(@RequestBody OfficialHolidayDto officialHolidayDto) {
        return new ResponseEntity<>(officialHolidayService.addNewOfficialHoliday(officialHolidayDto), HttpStatus.CREATED);
    }
}
