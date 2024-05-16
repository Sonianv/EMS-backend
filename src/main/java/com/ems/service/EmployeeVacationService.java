package com.ems.service;

import com.ems.dto.EmployeeVacationDto;
import com.ems.error.InvalidRequestBodyException;
import com.ems.error.ResourceNotFoundException;
import com.ems.model.EmployeeVacation;
import com.ems.repository.EmployeeVacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ems.converter.EmployeeVacationConverter.convertToEmployeeVacation;
import static com.ems.converter.EmployeeVacationConverter.convertToEmployeeVacationDto;
import static com.ems.model.EmployeeVacation.Status.PENDING;

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

    public EmployeeVacationDto updateEmployeeVacation(Long id, EmployeeVacationDto employeeVacationDto) {
        validateEmployeeVacationDto(employeeVacationDto);
        employeeVacationDto.setStatus(PENDING);
        return getUpdatedEmployeeVacationDto(id, employeeVacationDto);
    }

    public EmployeeVacationDto updateStatusOfEmployeeVacation(Long id, EmployeeVacationDto employeeVacationDto) {
        validateEmployeeVacationDto(employeeVacationDto);
        return getUpdatedEmployeeVacationDto(id, employeeVacationDto);
    }

    private EmployeeVacationDto getUpdatedEmployeeVacationDto(Long id, EmployeeVacationDto employeeVacationDto) {
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
    }

    private EmployeeVacation getEmployeeVacationById(Long id) {
        return employeeVacationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cannot find vacation with id " + id));
    }
}
