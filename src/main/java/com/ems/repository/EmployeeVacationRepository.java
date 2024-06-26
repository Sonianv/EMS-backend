package com.ems.repository;

import com.ems.model.EmployeeVacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.ems.model.EmployeeVacation.Status;

@Repository
public interface EmployeeVacationRepository extends JpaRepository<EmployeeVacation, Long> {
    Set<EmployeeVacation> findAllByEmployee_Id(Long id);

    List<EmployeeVacation> findByStatus(Status status);
}
