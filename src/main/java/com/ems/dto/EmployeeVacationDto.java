package com.ems.dto;

import com.ems.model.EmployeeVacation;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVacationDto {

    private Long id;
    @NotNull(message = "An employee id must be specified")
    private Long employeeId;
    @NotNull(message = "Start date must be specified")
    private LocalDate startDate;
    @NotNull(message = "End date must be specified")
    private LocalDate endDate;
    private EmployeeVacation.Status status;
}
