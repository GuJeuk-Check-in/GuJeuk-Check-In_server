package com.example.gujeuck_server.domain.organ.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserExcelResponse;
import com.example.gujeuck_server.infrastructure.excel.exception.ExcelGenerationException;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserExcelOutPutService {
    private final UserRepository userRepository;
    private final OrganFacade organFacade;

    private static final String EXCEL_MEDIA_TYPE_NAME = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final MediaType EXCEL_MEDIA_TYPE = MediaType.parseMediaType(EXCEL_MEDIA_TYPE_NAME);
    private static final String FILE_NAME = "%d년 %d월 %d일 회원 목록.xlsx";

    public ResponseEntity<byte[]> execute() {
        Organ organ = organFacade.currentOrgan();

        try {
            List<User> users = userRepository.findAllByOrganIdOrderByIdAsc(organ.getId());

            List<UserExcelResponse> responses = users.stream()
                    .map(UserExcelResponse::from)
                    .toList();

            byte[] excelFile = ExcelGenerator.generateUserExcel(responses);

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
        return String.format(FILE_NAME, now.getYear(), now.getMonthValue(), now.getDayOfMonth());
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
