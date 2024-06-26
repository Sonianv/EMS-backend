package com.ems.dto;

import com.ems.model.EmployeeWorkDay.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWorkDayDto {
    private Long id;
    @NotNull(message = "An employee id must be specified")
    private Long employeeId;
    @NotNull(message = "A day must be specified")
    private LocalDate day;
    @NotNull(message = "The start time must be specified")
    private LocalTime start;
    private LocalTime end;
    private float breakTime;
    private Status status;
    private Double workedHours;
    private String description;
}
