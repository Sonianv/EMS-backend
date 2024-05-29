package com.ems.repository;

import com.ems.model.Employee;
import com.ems.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    List<Employee> findAllByRole_NameOrderByLastNameAscFirstNameAsc(Role.ERole role);
}

