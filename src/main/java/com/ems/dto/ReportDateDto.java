package com.ems.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDateDto {

    @NotNull(message = "Month must be specified")
    private String month;
    @NotNull(message = "Year must be specified")
    private int year;
}
