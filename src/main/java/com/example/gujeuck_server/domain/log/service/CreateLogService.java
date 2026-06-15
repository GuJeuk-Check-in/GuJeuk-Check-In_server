package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.exception.DuplicateLogException;
import com.example.gujeuck_server.domain.log.presentation.dto.request.LogRequest;
import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.global.utility.DateFormatter;
import com.example.gujeuck_server.global.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateLogService {

    private final LogRepository logRepository;
    private final PurposeFacade purposeFacade;

    private static final String KOREAN_DATE_REGEX = "\\d{4}년\\d{2}월\\d{2}일";

    @Transactional
    public void execute(Organ organ, LogRequest request) {
        int currentYear = TimeProvider.nowYear();
        String formattedDate = resolveVisitDate(request.getVisitDate());
        String name = request.getName().trim();
        String purposeName = request.getPurpose().trim();
        String visitTime = request.getVisitTime().trim();

        Purpose purpose = purposeFacade.getPurpose(organ.getId(), purposeName);

        validateDuplicateLog(organ.getId(), name, request.getAge(), purpose.getPurposeName(), formattedDate, visitTime);

        Log log = createUseLog(request, name, purpose, formattedDate, visitTime, currentYear, organ);

        logRepository.save(log);
    }

    private void validateDuplicateLog(
            Long organId,
            String name,
            Age age,
            String purpose,
            String visitDate,
            String visitTime
    ) {
        if (logRepository.existsByOrganIdAndNameAndAgeAndPurposeAndVisitDateAndVisitTime(
                organId,
                name,
                age,
                purpose,
                visitDate,
                visitTime
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

    private Log createUseLog(
            LogRequest request,
            String name,
            Purpose purpose,
            String date,
            String time,
            int year,
            Organ organ
    ) {
        return Log.builder()
                .name(name)
                .age(request.getAge())
                .phone(request.getPhone().trim())
                .maleCount(request.getMaleCount())
                .femaleCount(request.getFemaleCount())
                .purpose(purpose.getPurposeName())
                .visitTime(time)
                .visitDate(date)
                .year(year)
                .privacyAgreed(request.isPrivacyAgreed())
                .organ(organ)
                .build();
    }
}
