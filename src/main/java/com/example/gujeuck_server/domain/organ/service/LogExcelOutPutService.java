package com.example.gujeuck_server.domain.organ.service;

import com.example.gujeuck_server.domain.log.presentation.dto.response.LogExcelResponse;
import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.infrastructure.excel.exception.ExcelGenerationException;
import com.example.gujeuck_server.infrastructure.excel.exception.InvalidDateException;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.infrastructure.excel.util.ExcelGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogExcelOutPutService {
    private final LogRepository logRepository;
    private final OrganFacade organFacade;

    private static final String EXCEL_MEDIA_TYPE_NAME = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final MediaType EXCEL_MEDIA_TYPE = MediaType.parseMediaType(EXCEL_MEDIA_TYPE_NAME);
    private static final String FILE_NAME = "%d년 %d월 이용 신청 현황.xlsx";
    private static final DateTimeFormatter YEAR_MONTH =
            DateTimeFormatter.ofPattern("yyyy-MM");

    private static final DateTimeFormatter VISIT_DATE =
            DateTimeFormatter.ofPattern("yyyy년MM월");

    public ResponseEntity<byte[]> execute(String yearMonth) {
        Organ organ = organFacade.currentOrgan();

        try {
            String visitDatePrefix = toVisitDatePrefix(yearMonth);


            List<Log> logs = logRepository.findAllByOrganIdAndVisitDateStartingWith(organ.getId(), visitDatePrefix);

            List<LogExcelResponse> responses = logs.stream()
                    .map(LogExcelResponse::from)
                    .toList();

            byte[] excelFile = ExcelGenerator.generateLogExcel(responses);

            String encodedFilename = encodeFileName(generateExcelFileName());
            HttpHeaders headers = buildExcelHeaders(encodedFilename);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelFile);

        } catch (IOException e) {
            throw ExcelGenerationException.EXCEPTION;
        }
    }
    private String toVisitDatePrefix(String yearMonth) {
        try {
            YearMonth ym = YearMonth.parse(yearMonth, YEAR_MONTH);

            return ym.format(VISIT_DATE);

        } catch (DateTimeParseException e) {
            throw InvalidDateException.EXCEPTION;
        }
    }

    private String generateExcelFileName() {
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
