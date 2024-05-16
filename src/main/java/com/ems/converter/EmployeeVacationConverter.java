package com.ems.converter;

import com.ems.dto.EmployeeVacationDto;
import com.ems.model.Employee;
import com.ems.model.EmployeeVacation;
import com.ems.service.EmployeeService;

public class EmployeeVacationConverter {

    public static EmployeeVacationDto convertToEmployeeVacationDto(EmployeeVacation employeeVacation) {
        return EmployeeVacationDto.builder()
                .id(employeeVacation.getId())
                .employeeId(employeeVacation.getEmployee().getId())
                .startDate(employeeVacation.getStartDate())
                .endDate(employeeVacation.getEndDate())
                .status(employeeVacation.getStatus())
                .build();
    }

    public static EmployeeVacation convertToEmployeeVacation(EmployeeVacationDto employeeVacationDto, EmployeeService employeeService) {
        Employee employee = employeeService.findById(employeeVacationDto.getEmployeeId());
        return EmployeeVacation.builder()
                .employee(employee)
                .startDate(employeeVacationDto.getStartDate())
                .endDate(employeeVacationDto.getEndDate())
                .status(employeeVacationDto.getStatus())
                .build();
    }
}
