package com.ems.repository;

import com.ems.model.EmployeeVacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeVacationRepository extends JpaRepository<EmployeeVacation, Long> {
}
