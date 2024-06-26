package com.ems.repository;

import com.ems.model.Employee;
import com.ems.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    List<Employee> findAllByRole_NameOrderByLastNameAscFirstNameAsc(Role.ERole role);

    @Query("SELECT e FROM Employee e WHERE e.role.name = :roleName AND (YEAR(e.startDate) < :year OR (YEAR(e.startDate) = :year AND MONTH(e.startDate) <= :month)) ORDER BY e.lastName ASC, e.firstName ASC")
    List<Employee> findAllByRoleNameAndStartDateBefore(
            @Param("roleName") Role.ERole role,
            @Param("year") int year,
            @Param("month") int month
    );
}

