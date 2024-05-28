package com.ems.converter;

import com.ems.dto.EmployeeWorkDayDto;
import com.ems.model.Employee;
import com.ems.model.EmployeeWorkDay;
import com.ems.model.EmployeeWorkDay.Status;
import com.ems.service.EmployeeService;

import java.time.temporal.ChronoUnit;

public class EmployeeWorkDayConverter {

    public static EmployeeWorkDayDto convertToEmployeeWorkDayDto(EmployeeWorkDay employeeWorkDay) {
        return EmployeeWorkDayDto.builder()
                .id(employeeWorkDay.getId())
                .employeeId(employeeWorkDay.getEmployee().getId())
                .day(employeeWorkDay.getDay())
                .start(employeeWorkDay.getStart())
                .end(employeeWorkDay.getEnd())
                .breakTime(employeeWorkDay.getBreakTime())
                .status(employeeWorkDay.getStatus())
                .workedHours(employeeWorkDay.getWorkedHours())
                .description(employeeWorkDay.getDescription())
                .build();

    }

    public static EmployeeWorkDay convertToEmployeeWorkDay(EmployeeWorkDayDto employeeWorkDayDto, EmployeeService employeeService) {
        Employee employee = employeeService.findById(employeeWorkDayDto.getEmployeeId());
        int expectedHours = employee.getProgram();

        return EmployeeWorkDay.builder()
                .employee(employee)
                .day(employeeWorkDayDto.getDay())
                .start(employeeWorkDayDto.getStart())
                .end(employeeWorkDayDto.getEnd())
                .breakTime(employeeWorkDayDto.getBreakTime())
                .workedHours(getWorkedHours(employeeWorkDayDto))
                .status(getStatusForEmployeeWorkDay(employeeWorkDayDto, expectedHours))
                .description(employeeWorkDayDto.getDescription())
                .build();
    }

    private static Status getStatusForEmployeeWorkDay(EmployeeWorkDayDto employeeWorkDayDto, int expectedHours) {
        if (employeeWorkDayDto.getEnd() == null) {
            return EmployeeWorkDay.Status.IN_PROGRESS;
        }
        Double workedHours = getWorkedHours(employeeWorkDayDto);
        if (workedHours > expectedHours) {
            return EmployeeWorkDay.Status.ABOVE_EXPECTED;
        } else if (workedHours < expectedHours) {
            return EmployeeWorkDay.Status.BELOW_EXPECTED;
        }
        return EmployeeWorkDay.Status.AS_EXPECTED;
    }

    private static Double getWorkedHours(EmployeeWorkDayDto employeeWorkDayDto) {
        if (employeeWorkDayDto.getEnd() == null) {
            return null;
        }
        return ChronoUnit.MINUTES.between(employeeWorkDayDto.getStart(), employeeWorkDayDto.getEnd()) / 60.0 - employeeWorkDayDto.getBreakTime();
    }
}
