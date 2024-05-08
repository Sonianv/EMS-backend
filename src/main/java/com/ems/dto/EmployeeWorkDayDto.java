package com.ems.dto;

import com.ems.model.EmployeeWorkDay.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWorkDayDto {
    private Long id;
    private Long employeeId;
    private LocalDate day;
    private LocalTime start;
    private LocalTime end;
    private float breakTime;
    private Status status;
    private Double workedHours;
}
