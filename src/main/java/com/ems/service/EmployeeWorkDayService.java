package com.ems.service;

import com.ems.converter.EmployeeWorkDayConverter;
import com.ems.dto.EmployeeWorkDayDto;
import com.ems.model.EmployeeWorkDay;
import com.ems.repository.EmployeeWorkDayRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeWorkDayService {

    private final EmployeeWorkDayRepository employeeWorkDayRepository;
    private final EmployeeService employeeService;

    public EmployeeWorkDayService(EmployeeWorkDayRepository employeeWorkDayRepository, EmployeeService employeeService) {
        this.employeeWorkDayRepository = employeeWorkDayRepository;
        this.employeeService = employeeService;
    }

    public EmployeeWorkDayDto addEmployeeWorkDay(EmployeeWorkDayDto employeeWorkDayDto) {
        EmployeeWorkDay employeeWorkDay = EmployeeWorkDayConverter.convertToEmployeeWorkDay(employeeWorkDayDto, employeeService);
        EmployeeWorkDay savedEmployeeWork = employeeWorkDayRepository.save(employeeWorkDay);
        return EmployeeWorkDayConverter.convertToEmployeeWorkDayDto(savedEmployeeWork);
    }
}
