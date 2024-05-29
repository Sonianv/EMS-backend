package com.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthEndClosingDto {

    private Long id;
    private Month month;
    private int year;
}
