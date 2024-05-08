package com.ems.repository;

import com.ems.model.EmployeeWorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EmployeeWorkDayRepository extends JpaRepository<EmployeeWorkDay, Long> {

    EmployeeWorkDay findEmployeeWorkDayById(Long id);

    Set<EmployeeWorkDay> findAllByEmployee_Id(Long id);
}