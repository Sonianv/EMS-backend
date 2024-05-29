package com.ems.dto;

import lombok.*;

import java.util.Map;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSummary {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private Map<Integer, Double> workedHoursOnDay;
    private int totalOfWorkedHours;

}
