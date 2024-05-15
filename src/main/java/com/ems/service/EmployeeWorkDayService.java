package com.ems.service;

import com.ems.dto.EmployeeWorkDayDto;
import com.ems.error.InvalidRequestBodyException;
import com.ems.error.ResourceNotFoundException;
import com.ems.model.EmployeeWorkDay;
import com.ems.repository.EmployeeWorkDayRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.ems.converter.EmployeeWorkDayConverter.convertToEmployeeWorkDay;
import static com.ems.converter.EmployeeWorkDayConverter.convertToEmployeeWorkDayDto;

@Service
public class EmployeeWorkDayService {

    private final EmployeeWorkDayRepository employeeWorkDayRepository;
    private final EmployeeService employeeService;

    public EmployeeWorkDayService(EmployeeWorkDayRepository employeeWorkDayRepository, EmployeeService employeeService) {
        this.employeeWorkDayRepository = employeeWorkDayRepository;
        this.employeeService = employeeService;
    }

    public EmployeeWorkDayDto addEmployeeWorkDay(EmployeeWorkDayDto employeeWorkDayDto) {
        validateEmployeeWorkDayDto(employeeWorkDayDto);
        EmployeeWorkDay employeeWorkDay = convertToEmployeeWorkDay(employeeWorkDayDto, employeeService);
        EmployeeWorkDay savedEmployeeWork = employeeWorkDayRepository.save(employeeWorkDay);
        return convertToEmployeeWorkDayDto(savedEmployeeWork);
    }

    public EmployeeWorkDayDto updateEmployeeWorkDay(Long id, EmployeeWorkDayDto employeeWorkDayDto) {
        validateEmployeeWorkDayDto(employeeWorkDayDto);
        EmployeeWorkDay newWorkDay = convertToEmployeeWorkDay(employeeWorkDayDto, employeeService);
        EmployeeWorkDay workDay = getEmployeeWorkDay(id);
        if (!newWorkDay.getDay().equals(workDay.getDay())) {
            throw new InvalidRequestBodyException("Cannot change the day of the work day");
        }
        workDay.update(newWorkDay);
        EmployeeWorkDay updatedEmployeeWork = employeeWorkDayRepository.save(workDay);
        return convertToEmployeeWorkDayDto(updatedEmployeeWork);
    }

    public EmployeeWorkDayDto getEmployeeWorkDayDto(Long id) {
        return convertToEmployeeWorkDayDto(getEmployeeWorkDay(id));
    }

    private EmployeeWorkDay getEmployeeWorkDay(Long id) {
        return employeeWorkDayRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cannot find employee work day with id " + id));
    }

    public Set<EmployeeWorkDayDto> getAllEmployeeWorkDays(Long id) {
        Set<EmployeeWorkDay> workDays = employeeWorkDayRepository.findAllByEmployee_Id(id);
        Set<EmployeeWorkDayDto> workDaysDto = new HashSet<>();
        for (EmployeeWorkDay workDay : workDays) {
            workDaysDto.add(convertToEmployeeWorkDayDto(workDay));
        }
        return workDaysDto;
    }

    private void validateEmployeeWorkDayDto(EmployeeWorkDayDto employeeWorkDayDto) {
        if (isWeekend(employeeWorkDayDto.getDay())) {
            throw new InvalidRequestBodyException("Cannot add work day on a weekend");
        } else if (employeeWorkDayDto.getEnd() != null && employeeWorkDayDto.getEnd().isBefore(employeeWorkDayDto.getStart())) {
            throw new InvalidRequestBodyException("Cannot set end time before start time");
        }
    }

    private boolean isWeekend(LocalDate day) {
        DayOfWeek dayOfWeek = day.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
