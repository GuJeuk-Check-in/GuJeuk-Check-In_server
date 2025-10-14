package com.example.gujeuck_server.domain.admin.service.excel;

import com.example.gujeuck_server.domain.admin.exception.ExcelGenerationException;
import com.example.gujeuck_server.domain.log.dto.response.LogResponse;
import com.example.gujeuck_server.domain.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogExcelOutPutService {
    private final LogRepository logRepository;

    private static final String FILE_NAME_PATTERN = "log_%d_%02d.xlsx";
    private static final String EXCEL_SHEET_NAME = "구즉 청소년 문화의집 월간 방문 기록";
    private static final MediaType EXCEL_MEDIA_TYPE = MediaType.APPLICATION_OCTET_STREAM;

    public ResponseEntity<byte[]> outputExcel() {
        try {
            List<LogResponse> logs = logRepository.findAllByCurrentMonth();

            byte[] excelFile = ExcelGenerator.generateLogExcel(logs);

            String encodedFilename = encodeFileName(generateExcelFileName());

            HttpHeaders headers = buildExcelHeaders(encodedFilename);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelFile);

        } catch (IOException e) {
            throw ExcelGenerationException.EXCEPTION;
        }
    }

    private String generateExcelFileName() {
        LocalDate now = LocalDate.now();
        return String.format(FILE_NAME_PATTERN, now.getYear(), now.getMonthValue());
    }

    private String encodeFileName(String filename) {
        return URLEncoder.encode(filename, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");
    }

    private HttpHeaders buildExcelHeaders(String encodedFilename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(EXCEL_MEDIA_TYPE);
        headers.set(
                HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename*=UTF-8''%s", encodedFilename)
        );
        return headers;
    }
}
