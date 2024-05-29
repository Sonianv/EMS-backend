package com.ems.converter;

import com.ems.dto.MonthEndClosingDto;
import com.ems.model.MonthEndClosing;

public class MonthEndClosingConverter {

    public static MonthEndClosingDto convertToMonthEndCLosingDto(MonthEndClosing monthEndClosing) {
        return MonthEndClosingDto.builder()
                .id(monthEndClosing.getId())
                .year(monthEndClosing.getYear())
                .month(monthEndClosing.getMonth())
                .build();
    }

    public static MonthEndClosing convertToMonthEndCLosing(MonthEndClosingDto monthEndClosingDto) {
        return MonthEndClosing.builder()
                .year(monthEndClosingDto.getYear())
                .month(monthEndClosingDto.getMonth())
                .build();
    }
}
