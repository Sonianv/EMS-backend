package com.ems.dto.vacation;

import lombok.*;

import java.time.LocalDate;

import static com.ems.model.EmployeeVacation.Status;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVacationResponse {

    private Long id;
    private Long employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;
}
