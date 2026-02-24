package com.example.gujeuck_server.infrastructure.excel.util;

import com.example.gujeuck_server.domain.log.presentation.dto.response.LogExcelResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGenerator { // 이건 엑셀 파일 내용 관련한 클래스
    private static final String[] HEADERS = {
            "NO", "날짜", "방문 시간", "성명", "나이", "남자 동행인 수", "여자 동행인 수", "연락처", "방문목적", "개인정보 제공 동의 여부"
    };
    private static final String sheetTitle = "구즉 청소년 문화의집 월간 방문 기록";

    public static byte[] generateLogExcel(List<LogExcelResponse> logs) throws IOException { // 아래 있는 메서드 가지고 조물조물 해서 엑셀 파일 만드는 메서드
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

    private static CellStyle createTitleStyle(Workbook workbook) { // 엑셀 제목 설정
        CellStyle style = workbook.createCellStyle(); // 제목 이쁘게 꾸미는거
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private static CellStyle createHeaderStyle(Workbook workbook) { // 엑셀 헤더 설정
        CellStyle style = workbook.createCellStyle(); // 엑셀 헤어 이쁘게 꾸미는거
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

    private static CellStyle createBodyStyle(Workbook workbook) { // 본문 내용 설정
        CellStyle style = workbook.createCellStyle(); // 본문 내용 이쁘게 꾸미는거
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        setBorders(style, BorderStyle.THIN);
        return style;
    }

    private static void setBorders(CellStyle style, BorderStyle borderStyle) { // 이것만 있으면 이제 제목, 헤더, 본문 설정을 한번에 가능
        style.setBorderTop(borderStyle);
        style.setBorderBottom(borderStyle);
        style.setBorderLeft(borderStyle);
        style.setBorderRight(borderStyle);
    }

    private static void createTitleRow(Sheet sheet, CellStyle style) { // 제목을 어떻게 할지 정하는거, sheet받고 그 sheet안에 어떤 제목 들어갈지
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


    private static void createBodyRows(Sheet sheet, List<LogExcelResponse> logs, CellStyle style) { // 얘도 본문 내용 정하는거임
        int rowIdx = 2;
        int no = 1;

        for (LogExcelResponse log : logs) {
            Row row = sheet.createRow(rowIdx++);
            row.setHeightInPoints(18);

            int col = 0;
            createCell(row, col++, no++, style);

            createCell(row, col++, log.visitDate(), style); // 이렇게 list로 가져온 값 하나하나 다 넣고 있는거임
            createCell(row, col++, log.visitTime(), style);
            createCell(row, col++, log.name(), style);
            createCell(row, col++, log.age().getLabel(), style);
            createCell(row, col++, log.maleCount(), style);
            createCell(row, col++, log.femaleCount(), style);
            createCell(row, col++, log.phone(), style);
            createCell(row, col++, log.purpose(), style);
            createCell(row, col++, log.privacyAgreed() ? "동의" : "미동의", style);
        }
    }

    private static void createCell(Row row, int colIdx, Object value, CellStyle style) { // 하나의 셀의 값을 넣는 메서드, 위에서 많이 사용된거 있음
        Cell cell = row.createCell(colIdx);
        if (value instanceof Number num) {
            cell.setCellValue(num.doubleValue());
        } else {
            cell.setCellValue(value != null ? value.toString() : "");
        }
        cell.setCellStyle(style);
    }


    private static void autoAdjustColumns(Sheet sheet) { // 열 넓이 자동조정
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
}
