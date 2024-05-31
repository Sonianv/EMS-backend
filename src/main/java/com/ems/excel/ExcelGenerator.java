package com.ems.excel;

import com.ems.dto.EmployeeDto;
import com.ems.dto.EmployeeWorkDayDto;
import com.ems.dto.ReportDateDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

import static java.util.Map.Entry;

public class ExcelGenerator {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMMM yyyy");

    public static File generateReport(Map<EmployeeDto, Set<EmployeeWorkDayDto>> employeeWithWorkDaysMap, ReportDateDto reportDateDto) {
        File report = null;
        try (InputStream fis = ExcelGenerator.class.getResourceAsStream("/templates/report_template.xlsx")) {
            Workbook template = new XSSFWorkbook(fis);

            Sheet firstSheet = template.getSheet("All_Employees");
            Cell styledCell = firstSheet.getRow(1).getCell(4);
            CellStyle style = template.createCellStyle();
            style.cloneStyleFrom(styledCell.getCellStyle());

            styledCell.setCellValue(reportDateDto.getMonth() + " " + reportDateDto.getYear());
            int rowNumber = 3;
            for (Entry<EmployeeDto, Set<EmployeeWorkDayDto>> employeeWithWorkDays : employeeWithWorkDaysMap.entrySet()) {
                EmployeeDto employee = employeeWithWorkDays.getKey();
                Row row = firstSheet.createRow(rowNumber++);

                setupCell(row, 0, employee.getLastName(), style, firstSheet);
                setupCell(row, 1, employee.getFirstName(), style, firstSheet);
                setupCell(row, 2, calcTotalWorkedHours(employeeWithWorkDays.getValue()), style, firstSheet);

                Sheet employeeSheet = template.cloneSheet(1);
                template.setSheetName(template.getSheetIndex(employeeSheet), employee.getFirstName() + "_" + employee.getLastName());
                Cell employeeCell = employeeSheet.getRow(1).getCell(0);
                employeeCell.setCellValue(employee.getLastName() + " " + employee.getFirstName());
                int workDayNumber = 3;
                for (EmployeeWorkDayDto workDay : employeeWithWorkDays.getValue()) {
                    Row workDayRow = employeeSheet.createRow(workDayNumber++);
                    setupCell(workDayRow, 0, workDay.getDay(), style, employeeSheet);
                    setupCell(workDayRow, 1, workDay.getStart(), style, employeeSheet);
                    setupCell(workDayRow, 2, workDay.getEnd(), style, employeeSheet);
                    setupCell(workDayRow, 3, workDay.getWorkedHours(), style, employeeSheet);
                    setupCell(workDayRow, 4, workDay.getBreakTime(), style, employeeSheet);
                }
            }
            template.removeSheetAt(1);

            String fileName = System.getProperty("user.home") + "\\Downloads\\" + "Employee_Report_" + reportDateDto.getMonth() + "_" + reportDateDto.getYear() + ".xlsx";
            report = new File(fileName);
            if (!report.exists()) {
                report.createNewFile();
            }
            try (FileOutputStream outputStream = new FileOutputStream(report)) {
                template.write(outputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return report;
    }

    private static void setupCell(Row row, int column, Object value, CellStyle style, Sheet sheet) {
        Cell cell = row.createCell(column);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof LocalDate) {
            cell.setCellValue(((LocalDate) value).format(formatter));
        } else if (value instanceof LocalTime) {
            cell.setCellValue(value.toString());
        } else {
            cell.setCellValue(value != null ? value.toString() : "-");
        }
        cell.setCellStyle(style);
        sheet.autoSizeColumn(column);
    }

    private static double calcTotalWorkedHours(Set<EmployeeWorkDayDto> workDays) {
        double total = 0.0;
        for (EmployeeWorkDayDto workDay : workDays) {
            if (workDay.getWorkedHours() != null) {
                total += workDay.getWorkedHours();
            }
        }
        return total;
    }
}
