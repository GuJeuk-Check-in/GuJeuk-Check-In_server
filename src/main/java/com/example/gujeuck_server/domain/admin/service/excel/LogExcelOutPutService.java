package com.example.gujeuck_server.domain.admin.service.excel;

import com.example.gujeuck_server.domain.admin.exception.ExcelGenerationException;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.log.dto.response.LogResponse;
import com.example.gujeuck_server.domain.log.entity.Log;
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
public class LogExcelOutPutService { // 이건 엑셀 다운로드 관련 클래스
    private final LogRepository logRepository;
    private final AdminFacade adminFacade;

    private static final String EXCEL_MEDIA_TYPE_NAME = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final MediaType EXCEL_MEDIA_TYPE = MediaType.parseMediaType(EXCEL_MEDIA_TYPE_NAME);
    private static final String FILE_NAME = "%d년 %d월 이용 신청 현황.xlsx";

    public ResponseEntity<byte[]> outputExcel(String yearMonth) {
//        adminFacade.currentUser();

        try {
            String visitDatePrefix = toVisitDatePrefix(yearMonth);


            List<Log> logs = logRepository.findAllByVisitDateStartingWith(visitDatePrefix);

            List<LogResponse> responses = logs.stream()
                    .map(LogResponse::from)
                    .toList();

            byte[] excelFile = ExcelGenerator.generateLogExcel(responses);

            // 파일 이름에 월 넣고 싶으면 이렇게
            String fileName = "logs-" + yearMonth + ".xlsx";
            String encodedFilename = encodeFileName(fileName);
            HttpHeaders headers = buildExcelHeaders(encodedFilename);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelFile);

        } catch (IOException e) {
            throw ExcelGenerationException.EXCEPTION;
        }
    }
    private String toVisitDatePrefix(String yearMonth) {
        if (!yearMonth.matches("\\d{4}-\\d{2}")) {
            throw new IllegalArgumentException("형식은 yyyy-MM 이어야 합니다. 예) 2025-11");
        }

        String[] parts = yearMonth.split("-");
        String year = parts[0];   // "2025"
        String month = parts[1];  // "11" (이미 2자리)

        // DB / 엑셀에 들어가는 형식: 2025년11월03일 → 여기서 월까지만 prefix 로 사용
        return year + "년" + month + "월";
    }


    private String generateExcelFileName() { // 다운로드 되는 파일 이름 정하는 메서드
        LocalDate now = LocalDate.now();
        return String.format(FILE_NAME, now.getYear(), now.getMonthValue());
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
