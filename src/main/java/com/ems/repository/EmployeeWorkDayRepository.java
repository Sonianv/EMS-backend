package com.ems.repository;

import com.ems.model.EmployeeWorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeWorkDayRepository extends JpaRepository<EmployeeWorkDay, Long> {

}
