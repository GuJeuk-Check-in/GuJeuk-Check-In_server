package com.example.gujeuck_server.domain.admin.service.excel;

import com.example.gujeuck_server.domain.log.dto.response.LogResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ExcelGenerator {
    private static final String[] HEADERS = {
            "NO", "날짜", "방문 시간", "성명", "나이", "남자 동행인 수", "여자 동행인 수", "연락처", "방문목적", "개인정보 제공 동의 여부"
    };
    private static final String sheetTitle = "구즉 청소년 문화의집 월간 방문 기록";

    public static byte[] generateLogExcel(List<LogResponse> logs) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(sheetTitle);

            CellStyle titleStyle = createTitleStyle(workbook);
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle bodyStyle = createBodyStyle(workbook);

            createTitleRow(sheet, titleStyle);
            createHeaderRow(sheet, headerStyle);
            createBodyRows(sheet, logs, bodyStyle);
            autoAdjustColumns(sheet);

            workbook.write(out);
            return out.toByteArray();
        }
    }

    private static CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        setBorders(style, BorderStyle.MEDIUM);
        return style;
    }

    private static CellStyle createBodyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        setBorders(style, BorderStyle.THIN);
        return style;
    }

    private static void setBorders(CellStyle style, BorderStyle borderStyle) {
        style.setBorderTop(borderStyle);
        style.setBorderBottom(borderStyle);
        style.setBorderLeft(borderStyle);
        style.setBorderRight(borderStyle);
    }

    private static void createTitleRow(Sheet sheet, CellStyle style) {
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(25);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(sheetTitle);
        titleCell.setCellStyle(style);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, HEADERS.length - 1));
    }

    private static void createHeaderRow(Sheet sheet, CellStyle style) {
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(20);

        for (int i = 0; i < HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(HEADERS[i]);
            cell.setCellStyle(style);
        }
    }


    private static void createBodyRows(Sheet sheet, List<LogResponse> logs, CellStyle style) {
        int rowIdx = 2;
        int no = 1;

        for (LogResponse log : logs) {
            Row row = sheet.createRow(rowIdx++);
            row.setHeightInPoints(18);

            int col = 0;
            createCell(row, col++, no++, style);

            String visitDateFormatted = formatFullDate(log.getVisitDate());
            createCell(row, col++, visitDateFormatted, style);

            createCell(row, col++, log.getVisitTime(), style);
            createCell(row, col++, log.getName(), style);
            createCell(row, col++, log.getAge().getLabel(), style);
            createCell(row, col++, log.getMaleCount(), style);
            createCell(row, col++, log.getFemaleCount(), style);
            createCell(row, col++, log.getPhone(), style);
            createCell(row, col++, log.getPurpose(), style);
            createCell(row, col++, log.isPrivacyAgreed() ? "동의" : "미동의", style);
        }
    }

    private static void createCell(Row row, int colIdx, Object value, CellStyle style) {
        Cell cell = row.createCell(colIdx);
        if (value instanceof Number num) {
            cell.setCellValue(num.doubleValue());
        } else {
            cell.setCellValue(value != null ? value.toString() : "");
        }
        cell.setCellStyle(style);
    }


    private static void autoAdjustColumns(Sheet sheet) {
        for (int i = 0; i < HEADERS.length; i++) {
            sheet.autoSizeColumn(i);

            int width = sheet.getColumnWidth(i);
            sheet.setColumnWidth(i, width + 1024);
        }

        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 3500);
        sheet.setColumnWidth(7, 6000);
        sheet.setColumnWidth(8, 6000);
        sheet.setColumnWidth(9, 5000);
    }


    private static String formatFullDate(String visitDate) {
        int currentYear = LocalDate.now().getYear();

        if (visitDate.contains("월")) {
            return currentYear + "년" + visitDate;
        } else if (visitDate.contains("-")) {
            String[] parts = visitDate.split("-");
            return String.format("%d년%s월%s일", currentYear, parts[0], parts[1]);
        } else {
            return currentYear + "년 " + visitDate;
        }
    }

}
