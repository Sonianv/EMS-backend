package com.ems.converter;

import com.ems.dto.vacation.EmployeeVacationRequest;
import com.ems.dto.vacation.EmployeeVacationResponse;
import com.ems.model.Employee;
import com.ems.model.EmployeeVacation;
import com.ems.service.EmployeeService;

public class EmployeeVacationConverter {

    public static EmployeeVacationRequest convertToEmployeeVacationRequest(EmployeeVacation employeeVacation) {
        return EmployeeVacationRequest.builder()
                .id(employeeVacation.getId())
                .employeeId(employeeVacation.getEmployee().getId())
                .startDate(employeeVacation.getStartDate())
                .endDate(employeeVacation.getEndDate())
                .status(employeeVacation.getStatus())
                .build();
    }

    public static EmployeeVacation convertToEmployeeVacation(EmployeeVacationRequest employeeVacationRequest, EmployeeService employeeService) {
        Employee employee = employeeService.findById(employeeVacationRequest.getEmployeeId());
        return EmployeeVacation.builder()
                .employee(employee)
                .startDate(employeeVacationRequest.getStartDate())
                .endDate(employeeVacationRequest.getEndDate())
                .status(employeeVacationRequest.getStatus())
                .build();
    }


    public static EmployeeVacationResponse convertToEmployeeVacationResponse(EmployeeVacation employeeVacation, EmployeeService employeeService) {
        Long employeeId = employeeVacation.getEmployee().getId();
        Employee employee = employeeService.findById(employeeId);
        return EmployeeVacationResponse.builder()
                .id(employeeVacation.getId())
                .employeeId(employeeId)
                .employeeFirstName(employee.getFirstName())
                .employeeLastName(employee.getLastName())
                .startDate(employeeVacation.getStartDate())
                .endDate(employeeVacation.getEndDate())
                .status(employeeVacation.getStatus())
                .build();
    }
}
