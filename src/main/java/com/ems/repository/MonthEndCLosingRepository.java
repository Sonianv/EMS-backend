package com.ems.repository;

import com.ems.model.MonthEndClosing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.Optional;

@Repository
public interface MonthEndCLosingRepository extends JpaRepository<MonthEndClosing, Long> {

    boolean existsByYearAndMonth(int year, Month month);

    Optional<MonthEndClosing> findFirstByYearAndMonth(int year, Month month);
}
