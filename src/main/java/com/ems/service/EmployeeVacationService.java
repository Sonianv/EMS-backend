package com.ems.service;

import com.ems.dto.vacation.EmployeeVacationRequest;
import com.ems.dto.vacation.EmployeeVacationResponse;
import com.ems.error.InvalidRequestBodyException;
import com.ems.error.ResourceNotFoundException;
import com.ems.model.EmployeeVacation;
import com.ems.repository.EmployeeVacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ems.converter.EmployeeVacationConverter.*;
import static com.ems.model.EmployeeVacation.Status;
import static com.ems.model.EmployeeVacation.Status.PENDING;
import static com.ems.model.EmployeeVacation.Status.findByName;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class EmployeeVacationService {

    private final EmployeeVacationRepository employeeVacationRepository;
    private final EmployeeService employeeService;

    public EmployeeVacationRequest addEmployeeVacation(EmployeeVacationRequest employeeVacationRequest) {
        validateEmployeeVacationDto(employeeVacationRequest);
        employeeVacationRequest.setStatus(PENDING);
        EmployeeVacation vacation = convertToEmployeeVacation(employeeVacationRequest, employeeService);
        EmployeeVacation savedVacation = employeeVacationRepository.save(vacation);
        return convertToEmployeeVacationRequest(savedVacation);
    }

    public EmployeeVacationRequest updateStatusOfEmployeeVacation(Long id, String statusName) {
        EmployeeVacation vacation = getEmployeeVacationById(id);
        Status newStatus = findByName(statusName);
        vacation.setStatus(newStatus);
        EmployeeVacation updatedEmployeeVacation = employeeVacationRepository.save(vacation);
        return convertToEmployeeVacationRequest(updatedEmployeeVacation);
    }

    public Set<EmployeeVacationRequest> getAllEmployeeVacationsByEmployeeId(Long id) {
        Set<EmployeeVacation> vacations = employeeVacationRepository.findAllByEmployee_Id(id);
        Set<EmployeeVacationRequest> vacationsDto = new HashSet<>();
        for (EmployeeVacation vacation : vacations) {
            vacationsDto.add(convertToEmployeeVacationRequest(vacation));
        }
        return vacationsDto;
    }

    public EmployeeVacationRequest updateEmployeeVacation(Long id, EmployeeVacationRequest employeeVacationRequest) {
        validateEmployeeVacationDto(employeeVacationRequest);
        employeeVacationRequest.setStatus(PENDING);
        EmployeeVacation newVacation = convertToEmployeeVacation(employeeVacationRequest, employeeService);
        EmployeeVacation vacation = getEmployeeVacationById(id);
        vacation.update(newVacation);
        EmployeeVacation updatedEmployeeVacation = employeeVacationRepository.save(vacation);
        return convertToEmployeeVacationRequest(updatedEmployeeVacation);
    }

    public List<EmployeeVacationResponse> getAllEmployeeVacationsByStatus(String statusName) {
        Status status = findByName(statusName);
        List<EmployeeVacation> employeeVacationList = employeeVacationRepository.findByStatus(status);
        return employeeVacationList.stream().map(v -> convertToEmployeeVacationResponse(v, employeeService)).collect(toList());
    }

    private static void validateEmployeeVacationDto(EmployeeVacationRequest employeeVacationRequest) {
        if (employeeVacationRequest.getStartDate().isAfter(employeeVacationRequest.getEndDate())) {
            throw new InvalidRequestBodyException("Cannot have start date after end date");
        }
        if (isWeekend(employeeVacationRequest.getStartDate()) || isWeekend(employeeVacationRequest.getEndDate())) {
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
