package com.ems.service;

import com.ems.converter.OfficialHolidayConverter;
import com.ems.dto.OfficialHolidayDto;
import com.ems.model.OfficialHoliday;
import com.ems.repository.OfficialHolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ems.converter.OfficialHolidayConverter.convertToOfficialHoliday;
import static com.ems.converter.OfficialHolidayConverter.convertToOfficialHolidayDto;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class OfficialHolidayService {

    private final OfficialHolidayRepository officialHolidayRepository;


    public List<OfficialHolidayDto> getAllOfficialHolidays() {
        return officialHolidayRepository.findAll().stream().map(OfficialHolidayConverter::convertToOfficialHolidayDto).collect(toList());
    }

    public OfficialHolidayDto addNewOfficialHoliday(OfficialHolidayDto officialHolidayDto) {
        OfficialHoliday officialHoliday = convertToOfficialHoliday(officialHolidayDto);
        OfficialHoliday savedOfficialHoliday = officialHolidayRepository.save(officialHoliday);
        return convertToOfficialHolidayDto(savedOfficialHoliday);
    }
}
