package com.ems.converter;

import com.ems.dto.EmployeeWorkDayDto;
import com.ems.model.Employee;
import com.ems.model.EmployeeWorkDay;
import com.ems.model.EmployeeWorkDay.Status;
import com.ems.service.EmployeeService;

import java.time.temporal.ChronoUnit;

public class EmployeeWorkDayConverter {

    public static EmployeeWorkDayDto convertToEmployeeWorkDayDto(EmployeeWorkDay employeeWorkDay) {
        return new EmployeeWorkDayDto(
                employeeWorkDay.getId(),
                employeeWorkDay.getEmployee().getId(),
                employeeWorkDay.getDay(),
                employeeWorkDay.getStart(),
                employeeWorkDay.getEnd(),
                employeeWorkDay.getBreakTime(),
                employeeWorkDay.getStatus(),
                employeeWorkDay.getWorkedHours()
        );
    }

    public static EmployeeWorkDay convertToEmployeeWorkDay(EmployeeWorkDayDto employeeWorkDayDto, EmployeeService employeeService) {
        EmployeeWorkDay employeeWorkDay = new EmployeeWorkDay();
        Employee employee = employeeService.findById(employeeWorkDayDto.getEmployeeId());

        employeeWorkDay.setEmployee(employee);
        employeeWorkDay.setDay(employeeWorkDayDto.getDay());
        employeeWorkDay.setStart(employeeWorkDayDto.getStart());
        employeeWorkDay.setEnd(employeeWorkDayDto.getEnd());
        employeeWorkDay.setBreakTime(employeeWorkDayDto.getBreakTime());
        int expectedHours = employee.getProgram();
        employeeWorkDay.setWorkedHours(getWorkedHours(employeeWorkDayDto));
        employeeWorkDay.setStatus(getStatusForEmployeeWorkDay(employeeWorkDayDto, expectedHours));
        return employeeWorkDay;
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
