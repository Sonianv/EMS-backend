package com.ems.service;

import com.ems.dto.EmployeeVacationDto;
import com.ems.error.InvalidRequestBodyException;
import com.ems.error.ResourceNotFoundException;
import com.ems.model.EmployeeVacation;
import com.ems.repository.EmployeeVacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.ems.converter.EmployeeVacationConverter.convertToEmployeeVacation;
import static com.ems.converter.EmployeeVacationConverter.convertToEmployeeVacationDto;
import static com.ems.model.EmployeeVacation.Status;
import static com.ems.model.EmployeeVacation.Status.PENDING;
import static com.ems.model.EmployeeVacation.Status.findByName;

@Service
@RequiredArgsConstructor
public class EmployeeVacationService {

    private final EmployeeVacationRepository employeeVacationRepository;
    private final EmployeeService employeeService;

    public EmployeeVacationDto addEmployeeVacation(EmployeeVacationDto employeeVacationDto) {
        validateEmployeeVacationDto(employeeVacationDto);
        employeeVacationDto.setStatus(PENDING);
        EmployeeVacation vacation = convertToEmployeeVacation(employeeVacationDto, employeeService);
        EmployeeVacation savedVacation = employeeVacationRepository.save(vacation);
        return convertToEmployeeVacationDto(savedVacation);
    }

    public EmployeeVacationDto updateStatusOfEmployeeVacation(Long id, String statusName) {
        EmployeeVacation vacation = getEmployeeVacationById(id);
        Status newStatus = findByName(statusName);
        vacation.setStatus(newStatus);
        EmployeeVacation updatedEmployeeVacation = employeeVacationRepository.save(vacation);
        return convertToEmployeeVacationDto(updatedEmployeeVacation);
    }

    public Set<EmployeeVacationDto> getAllEmployeeVacationsByEmployeeId(Long id) {
        Set<EmployeeVacation> vacations = employeeVacationRepository.findAllByEmployee_Id(id);
        Set<EmployeeVacationDto> vacationsDto = new HashSet<>();
        for (EmployeeVacation vacation : vacations) {
            vacationsDto.add(convertToEmployeeVacationDto(vacation));
        }
        return vacationsDto;
    }

    public EmployeeVacationDto updateEmployeeVacation(Long id, EmployeeVacationDto employeeVacationDto) {
        validateEmployeeVacationDto(employeeVacationDto);
        employeeVacationDto.setStatus(PENDING);
        EmployeeVacation newVacation = convertToEmployeeVacation(employeeVacationDto, employeeService);
        EmployeeVacation vacation = getEmployeeVacationById(id);
        vacation.update(newVacation);
        EmployeeVacation updatedEmployeeVacation = employeeVacationRepository.save(vacation);
        return convertToEmployeeVacationDto(updatedEmployeeVacation);
    }

    private static void validateEmployeeVacationDto(EmployeeVacationDto employeeVacationDto) {
        if (employeeVacationDto.getStartDate().isAfter(employeeVacationDto.getEndDate())) {
            throw new InvalidRequestBodyException("Cannot have start date after end date");
        }
        if (isWeekend(employeeVacationDto.getStartDate()) || isWeekend(employeeVacationDto.getEndDate())) {
            throw new InvalidRequestBodyException("Cannot have start or end of vacation in weekend.");
        }
    }

    private EmployeeVacation getEmployeeVacationById(Long id) {
        return employeeVacationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cannot find vacation with id " + id));
    }

    private static boolean isWeekend(LocalDate day) {
        DayOfWeek dayOfWeek = day.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
