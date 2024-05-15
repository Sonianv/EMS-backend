package com.ems.repository;

import com.ems.model.OfficialHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficialHolidayRepository extends JpaRepository<OfficialHoliday, Long> {

    List<OfficialHoliday> findAll();
}
