package com.example.gujeuck_server.domain.admin.service.excel;

import com.example.gujeuck_server.domain.log.dto.response.LogResponse;
import com.example.gujeuck_server.global.utility.ExcelHeaderStyleService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGenerator {
    private static final String[] HEADERS = {
            "NO", "날짜", "방문시간", "나이", "남자 동행인 수", "여자 동행인 수", "연락처", "방문목적", "개인정보 제공 동의 여부"
    };

    public static byte[] generateLogExcel(List<LogResponse> logs) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("구즉 청소년 문화의집 월간 방문 기록");
            createHeader(sheet, workbook);
            createBody(sheet, logs);

            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    private static void createHeader(Sheet sheet, Workbook workbook) {
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = ExcelHeaderStyleService.createHeaderStyle(workbook);

        for (int i = 0; i < HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(HEADERS[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private static void createBody(Sheet sheet, List<LogResponse> logs) {
        int rowIdx = 1;
        int no = 1;

        for (LogResponse log : logs) {
            Row row = sheet.createRow(rowIdx++);

            int col = 0;
            row.createCell(col++).setCellValue(no++);
            row.createCell(col++).setCellValue(log.getVisitDate());
            row.createCell(col++).setCellValue(log.getName());
            row.createCell(col++).setCellValue(
                    log.getAge() != null ? log.getAge().getLabel() : ""
            );
            row.createCell(col++).setCellValue(log.getMaleCount());
            row.createCell(col++).setCellValue(log.getFemaleCount());
            row.createCell(col++).setCellValue(log.getPhone());
            row.createCell(col++).setCellValue(log.getPurpose());
            row.createCell(col++).setCellValue(log.isPrivacyAgreed() ? "동의" : "미동의");
        }
    }
}
