package com.ems.dto.vacation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.ems.model.EmployeeVacation.Status;
import static com.ems.model.EmployeeVacation.Type;

@Data
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
    private Type type;
}
