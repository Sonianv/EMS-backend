package com.ems.dto.vacation;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

import static com.ems.model.EmployeeVacation.Status;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVacationRequest {

    private Long id;
    @NotNull(message = "An employee id must be specified")
    private Long employeeId;
    @NotNull(message = "Start date must be specified")
    private LocalDate startDate;
    @NotNull(message = "End date must be specified")
    private LocalDate endDate;
    private Status status;
}
