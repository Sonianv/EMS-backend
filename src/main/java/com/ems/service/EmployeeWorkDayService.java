package com.ems.service;

import com.ems.converter.EmployeeWorkDayConverter;
import com.ems.dto.EmployeeWorkDayDto;
import com.ems.model.EmployeeWorkDay;
import com.ems.repository.EmployeeWorkDayRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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

    public EmployeeWorkDayDto updateEmployeeWorkDay(Long id, EmployeeWorkDayDto employeeWorkDayDto) {
        EmployeeWorkDay newWorkDay = EmployeeWorkDayConverter.convertToEmployeeWorkDay(employeeWorkDayDto, employeeService);
        EmployeeWorkDay workDay = getEmployeeWorkDay(id);
        workDay.update(newWorkDay);
        EmployeeWorkDay updatedEmployeeWork = employeeWorkDayRepository.save(workDay);
        return EmployeeWorkDayConverter.convertToEmployeeWorkDayDto(updatedEmployeeWork);
    }

    public EmployeeWorkDay getEmployeeWorkDay(Long id) {
        return employeeWorkDayRepository.findEmployeeWorkDayById(id);
    }

    public Set<EmployeeWorkDayDto> getAllEmployeeWorkDays(Long id) {
        Set<EmployeeWorkDay> workDays = employeeWorkDayRepository.findAllByEmployee_Id(id);
        Set<EmployeeWorkDayDto> workDaysDto = new HashSet<>();
        for (EmployeeWorkDay workDay : workDays) {
            workDaysDto.add(EmployeeWorkDayConverter.convertToEmployeeWorkDayDto(workDay));
        }
        return workDaysDto;
    }
}
