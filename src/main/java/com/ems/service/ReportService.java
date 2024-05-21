package com.ems.service;

import com.ems.dto.EmployeeDto;
import com.ems.dto.EmployeeWorkDayDto;
import com.ems.dto.ReportDateDto;
import com.ems.excel.ExcelGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final EmployeeService employeeService;
    private final EmployeeWorkDayService employeeWorkDayService;

    public void generateReport(ReportDateDto reportDateDto) {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        Map<EmployeeDto, Set<EmployeeWorkDayDto>> employeeWithWorkDaysMap = new HashMap<>();

        for (EmployeeDto employee : employees) {
            Set<EmployeeWorkDayDto> workDays = employeeWorkDayService.getAllEmployeeWorkDaysFromMonth(reportDateDto.getMonth(), reportDateDto.getYear(), employee.getId());
            employeeWithWorkDaysMap.put(employee, workDays);
        }
        ExcelGenerator.generateReport(employeeWithWorkDaysMap, reportDateDto);
    }
}
