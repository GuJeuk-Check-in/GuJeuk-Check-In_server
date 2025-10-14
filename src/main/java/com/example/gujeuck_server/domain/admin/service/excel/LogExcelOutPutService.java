package com.example.gujeuck_server.domain.admin.service.excel;

import com.example.gujeuck_server.domain.admin.exception.ExcelGenerationException;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
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
    private final AdminFacade adminFacade;

    private static final String EXCEL_MEDIA_TYPE_NAME = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final MediaType EXCEL_MEDIA_TYPE = MediaType.parseMediaType(EXCEL_MEDIA_TYPE_NAME);

    public ResponseEntity<byte[]> outputExcel() {
        adminFacade.currentUser();

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
        return String.format("%d년 %d월 이용 신청 현황.xlsx", now.getYear(), now.getMonthValue());
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
