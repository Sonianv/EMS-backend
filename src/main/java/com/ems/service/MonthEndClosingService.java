package com.ems.service;

import com.ems.converter.MonthEndClosingConverter;
import com.ems.dto.MonthEndClosingDto;
import com.ems.error.MonthClosedException;
import com.ems.model.MonthEndClosing;
import com.ems.repository.MonthEndCLosingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;

import static com.ems.converter.MonthEndClosingConverter.convertToMonthEndCLosing;
import static com.ems.converter.MonthEndClosingConverter.convertToMonthEndCLosingDto;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class MonthEndClosingService {

    private final MonthEndCLosingRepository monthEndCLosingRepository;

    public boolean doesMonthEndClosingExist(int year, Month month) {
        return monthEndCLosingRepository.existsByYearAndMonth(year, month);
    }

    public MonthEndClosingDto addNewMonthEndClosing(MonthEndClosingDto monthEndClosingDto) {
        MonthEndClosing monthEndClosing = convertToMonthEndCLosing(monthEndClosingDto);
        if (doesMonthEndClosingExist(monthEndClosingDto.getYear(), monthEndClosingDto.getMonth())) {
            throw new MonthClosedException("This month was already closed.");
        }
        MonthEndClosing savedMonthEndClosing = monthEndCLosingRepository.save(monthEndClosing);
        return convertToMonthEndCLosingDto(savedMonthEndClosing);
    }

    public MonthEndClosingDto getMonthEndClosingByYearAndMonth(int year, Month month) {
        MonthEndClosing monthEndClosing = monthEndCLosingRepository.findFirstByYearAndMonth(year, month).orElseThrow(() -> new MonthClosedException("Month is not closed"));
        return convertToMonthEndCLosingDto(monthEndClosing);

    }

    public List<MonthEndClosingDto> getAllMonthEndClosings() {
        return monthEndCLosingRepository.findAll().stream().map(MonthEndClosingConverter::convertToMonthEndCLosingDto).collect(toList());
    }
}
