package com.ems.converter;

import com.ems.dto.OfficialHolidayDto;
import com.ems.model.OfficialHoliday;

public class OfficialHolidayConverter {

    public static OfficialHolidayDto convertToOfficialHolidayDto(OfficialHoliday officialHoliday) {
        return OfficialHolidayDto.builder()
                .id(officialHoliday.getId())
                .date(officialHoliday.getDate())
                .name(officialHoliday.getName())
                .build();
    }

    public static OfficialHoliday convertToOfficialHoliday(OfficialHolidayDto officialHolidayDto) {
        return OfficialHoliday.builder()
                .date(officialHolidayDto.getDate())
                .name(officialHolidayDto.getName())
                .build();
    }
}
