package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.exception.DuplicateLogException;
import com.example.gujeuck_server.domain.log.facade.LogFacade;
import com.example.gujeuck_server.domain.log.presentation.dto.request.LogRequest;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.global.utility.DateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UpdateLogService {
    private final LogFacade logFacade;
    private final LogRepository logRepository;

    private static final String KOREAN_DATE_REGEX = "\\d{4}년\\d{2}월\\d{2}일";

    @Transactional
    public void execute(Long organId, Long logId, LogRequest request) {
        Log log = logFacade.getLogByIdAndOrganId(logId, organId);
        String name = request.getName().trim();
        String purpose = request.getPurpose().trim();
        String visitDate = resolveVisitDate(request.getVisitDate());
        String visitTime = request.getVisitTime().trim();

        validateDuplicateLog(organId, name, request.getAge(), purpose, visitDate, visitTime, logId);

        log.updateLog(
                name,
                request.getAge(),
                request.getPhone().trim(),
                request.getMaleCount(),
                request.getFemaleCount(),
                purpose,
                visitDate,
                visitTime,
                request.isPrivacyAgreed()
        );
    }

    private void validateDuplicateLog(
            Long organId,
            String name,
            Age age,
            String purpose,
            String visitDate,
            String visitTime,
            Long logId
    ) {
        if (logRepository.existsByOrganIdAndNameAndAgeAndPurposeAndVisitDateAndVisitTimeAndIdNot(
                organId,
                name,
                age,
                purpose,
                visitDate,
                visitTime,
                logId
        )) {
            throw DuplicateLogException.EXCEPTION;
        }
    }

    private String resolveVisitDate(String requestDate) {
        if (requestDate == null || requestDate.isBlank()) {
            return DateFormatter.LocalDateForm(LocalDate.now());
        }

        if (requestDate.matches(KOREAN_DATE_REGEX)) {
            return requestDate;
        }

        return DateFormatter.LocalDateForm(LocalDate.parse(requestDate));
    }
}
