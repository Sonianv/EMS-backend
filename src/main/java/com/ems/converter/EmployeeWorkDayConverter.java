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
                employeeWorkDay.getStartTime(),
                employeeWorkDay.getEndTime(),
                employeeWorkDay.getBreakTime(),
                employeeWorkDay.getStatus()
        );
    }

    public static EmployeeWorkDay convertToEmployeeWorkDay(EmployeeWorkDayDto employeeWorkDayDto, EmployeeService employeeService) {
        EmployeeWorkDay employeeWorkDay = new EmployeeWorkDay();
        Employee employee = employeeService.findById(employeeWorkDayDto.getEmployeeId());

        employeeWorkDay.setEmployee(employee);
        employeeWorkDay.setStartTime(employeeWorkDayDto.getStartTime());
        employeeWorkDay.setEndTime(employeeWorkDayDto.getEndTime());
        employeeWorkDay.setBreakTime(employeeWorkDayDto.getBreakTime());
        int expectedHours = employee.getProgram();
        employeeWorkDay.setStatus(getStatusForEmployeeWorkDay(employeeWorkDayDto, expectedHours));
        return employeeWorkDay;
    }

    private static Status getStatusForEmployeeWorkDay(EmployeeWorkDayDto employeeWorkDayDto, int expectedHours) {
        if (employeeWorkDayDto.getEndTime() == null) {
            return EmployeeWorkDay.Status.IN_PROGRESS;
        }
        float workedHours = ChronoUnit.HOURS.between(employeeWorkDayDto.getStartTime(), employeeWorkDayDto.getEndTime()) - employeeWorkDayDto.getBreakTime();
        if (workedHours > expectedHours) {
            return EmployeeWorkDay.Status.ABOVE_EXPECTED;
        } else if (workedHours < expectedHours) {
            return EmployeeWorkDay.Status.BELOW_EXPECTED;
        }
        return EmployeeWorkDay.Status.AS_EXPECTED;
    }
}
