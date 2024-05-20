package com.ems.excel;

import com.ems.dto.EmployeeDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelGenerator {

//    public static void main(String[] args) {
//        readTemplate();
//    }
//
//    public static void readTemplate() {
//        try (InputStream fis = ExcelGenerator.class.getResourceAsStream("/templates/report_template.xlsx")) {
//            Workbook workbook = new XSSFWorkbook(fis);
//            System.out.println("heeello");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static void generateReport(List<EmployeeDto> employees) {

        try (InputStream fis = ExcelGenerator.class.getResourceAsStream("/templates/report_template.xlsx")) {
            Workbook template = new XSSFWorkbook(fis);

            Sheet firstSheet = template.getSheet("All_Employees");
            Cell styledCell = firstSheet.getRow(1).getCell(4);
            CellStyle style = template.createCellStyle();
            style.cloneStyleFrom(styledCell.getCellStyle());
            
            styledCell.setCellValue(LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM yyyy")));
            int rowNumber = 3;
            for (EmployeeDto employee : employees) {
                Row row = firstSheet.createRow(rowNumber++);
                Cell firstNameCell = row.createCell(0);
                firstNameCell.setCellValue(employee.getFirstName());
                firstNameCell.setCellStyle(style);
                Cell lastNameCell = row.createCell(1);
                lastNameCell.setCellValue(employee.getLastName());
                lastNameCell.setCellStyle(style);
            }

//            for (EmployeeDto employee : employees) {
//                Sheet employeeSheet = template.createSheet(employee.getId() + "_" + employee.getFirstName() + "_" + employee.getLastName());
//                Row row = employeeSheet.createRow(0);
//                row.createCell(0).setCellValue("First Name:");
//                row.createCell(1).setCellValue(employee.getFirstName());
//                row = employeeSheet.createRow(1);
//                row.createCell(0).setCellValue("Last Name:");
//                row.createCell(1).setCellValue(employee.getLastName());
//            }
            String currentMonthYear = LocalDate.now().format(DateTimeFormatter.ofPattern("MM_yyyy"));
            String fileName = System.getProperty("user.home") + "\\Downloads\\" + "Employee_Report_" + currentMonthYear + ".xlsx";
            try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
                template.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
