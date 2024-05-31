package com.ems.service;

import com.ems.converter.EmployeeConverter;
import com.ems.dto.EmployeeDto;
import com.ems.dto.EmployeeSummary;
import com.ems.model.Employee;
import com.ems.model.EmployeeWorkDay;
import com.ems.repository.EmployeeRepository;
import com.ems.repository.EmployeeWorkDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.ems.model.Role.ERole.USER;

@Service
@RequiredArgsConstructor
public class EmployeeService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeWorkDayRepository employeeWorkDayRepository;

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot find employee with id " + id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found"));
    }

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAllByRole_NameOrderByLastNameAscFirstNameAsc(USER).stream().map(EmployeeConverter::convertToEmployeeDto).toList();
    }

    public List<EmployeeSummary> getMonthSummary(int year, Month month) {
        List<EmployeeDto> employees = employeeRepository.findAllByRoleNameAndStartDateBefore(USER, year, month.getValue()).stream().map(EmployeeConverter::convertToEmployeeDto).toList();
        List<EmployeeSummary> employeeSummaryList = new ArrayList<>();

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plus(1, ChronoUnit.MONTHS).minusDays(1);
        for (EmployeeDto employee : employees) {
            EmployeeSummary summary = EmployeeSummary.builder()
                    .employeeId(employee.getId())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .email(employee.getEmail()).build();

            Set<EmployeeWorkDay> workDays = employeeWorkDayRepository.findByEmployee_IdAndDayBetween(employee.getId(), startDate, endDate);

            Map<Integer, Double> workHours = initializeWorkingDays(endDate, year, month);
            int totalHours = 0;
            for (EmployeeWorkDay workDay : workDays) {
                LocalDate date = workDay.getDay();
                if (date.getDayOfWeek().getValue() < 6) {
                    int dayOfMonth = date.getDayOfMonth();
                    double hours = workDay.getWorkedHours() != null ? workDay.getWorkedHours() : 0.0;
                    workHours.put(dayOfMonth, hours);
                    totalHours += hours;
                }
            }
            summary.setWorkedHoursOnDay(workHours);
            summary.setTotalOfWorkedHours(totalHours);
            employeeSummaryList.add(summary);
        }
        return employeeSummaryList;
    }

    private static Map<Integer, Double> initializeWorkingDays(LocalDate endDate, int year, Month month) {
        Map<Integer, Double> workHours = new HashMap<>();
        for (int day = 1; day <= endDate.getDayOfMonth(); day++) {
            LocalDate currentDate = LocalDate.of(year, month, day);
            if (currentDate.getDayOfWeek().getValue() < 6) {
                workHours.put(day, 0.0);
            }
        }
        return workHours;
    }
}
