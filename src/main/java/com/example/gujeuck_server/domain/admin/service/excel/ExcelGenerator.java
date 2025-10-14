package com.example.gujeuck_server.domain.admin.service.excel;

import com.example.gujeuck_server.domain.log.dto.response.LogResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGenerator {
    private static final String[] HEADERS = {
            "NO", "날짜", "성명", "나이", "남자 동행인 수", "여자 동행인 수", "연락처", "방문목적", "개인정보 제공 동의 여부"
    };
    private static final String sheetTitle = "구즉 청소년 문화의집 월간 방문 기록";

    public static byte[] generateLogExcel(List<LogResponse> logs) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(sheetTitle);

            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle bodyStyle = createBodyStyle(workbook);

            createHeader(sheet, headerStyle);
            createBody(sheet, logs, bodyStyle);

            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    // 🔹 헤더 스타일
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
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private static CellStyle createBodyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private static void createHeader(Sheet sheet, CellStyle style) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(HEADERS[i]);
            cell.setCellStyle(style);
        }
    }

    private static void createBody(Sheet sheet, List<LogResponse> logs, CellStyle style) {
        int rowIdx = 1;
        int no = 1;

        for (LogResponse log : logs) {
            Row row = sheet.createRow(rowIdx++);
            int col = 0;

            createCell(row, col++, no++, style);
            createCell(row, col++, log.getVisitDate(), style);
            createCell(row, col++, log.getName(), style);
            createCell(row, col++, log.getAge().getLabel(), style); // Age Enum → label 변환
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
}
