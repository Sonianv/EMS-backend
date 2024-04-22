package com.ems.dto;

import com.ems.model.EmployeeWorkDay.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWorkDayDto {
    private Long id;
    private Long employeeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private float breakTime;
    private Status status;
}
