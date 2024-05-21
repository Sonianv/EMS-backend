package com.ems.service;

import com.ems.dto.EmployeeDto;
import com.ems.dto.EmployeeWorkDayDto;
import com.ems.dto.ReportDateDto;
import com.ems.excel.ExcelGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final EmployeeService employeeService;
    private final EmployeeWorkDayService employeeWorkDayService;

    public File generateReport(ReportDateDto reportDateDto) {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        var sortedEmployees = employees.stream().sorted(Comparator.comparing(EmployeeDto::getLastName)
                .thenComparing(EmployeeDto::getFirstName)).toList();

        Map<EmployeeDto, Set<EmployeeWorkDayDto>> employeeWithWorkDaysMap = new TreeMap<>(Comparator.comparing(EmployeeDto::getLastName)
                .thenComparing(EmployeeDto::getFirstName));
        for (EmployeeDto employee : sortedEmployees) {
            Set<EmployeeWorkDayDto> workDays = employeeWorkDayService.getAllEmployeeWorkDaysFromMonth(reportDateDto.getMonth(), reportDateDto.getYear(), employee.getId());
            SortedSet<EmployeeWorkDayDto> sortedWorkDays = new TreeSet<>(Comparator.comparing(EmployeeWorkDayDto::getDay));
            sortedWorkDays.addAll(workDays);
            employeeWithWorkDaysMap.put(employee, sortedWorkDays);
        }
        return ExcelGenerator.generateReport(employeeWithWorkDaysMap, reportDateDto);
    }
}
