package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.log.presentation.dto.request.LogRequest;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;

import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import com.example.gujeuck_server.global.utility.DateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CreateLogService {

    private final LogRepository logRepository;
    private final OrganFacade organFacade;
    private final PurposeFacade purposeFacade;

    private static final String TIME = "HH:mm";
    private static final String KOREAN_DATE_REGEX = "\\d{4}년\\d{2}월\\d{2}일";

    @Transactional
    public void execute(LogRequest request) {

        Organ organ = organFacade.currentUser();

        String visitTime = LocalTime.now().format(DateTimeFormatter.ofPattern(TIME));

        int currentYear = LocalDate.now().getYear();

        Purpose purpose = purposeFacade.getPurpose(request.getPurpose());

        String formattedDate = resolveVisitDate(request.getVisitDate());

        Log log = createUseLog(request, purpose, formattedDate, visitTime, currentYear, organ);
        logRepository.save(log);
    }

    private String resolveVisitDate(String requestDate) {
        if (requestDate == null || requestDate.isBlank()) {
            return DateFormatter.LocalDateForm(LocalDate.now());
        }

        if (isKoreanDateFormat(requestDate)) {
            return requestDate;
        }

        LocalDate parsed = LocalDate.parse(requestDate);
        return DateFormatter.LocalDateForm(parsed);
    }


    private boolean isKoreanDateFormat(String date) {
        return date != null && date.matches(KOREAN_DATE_REGEX);
    }

    private Log createUseLog(
            LogRequest request,
            Purpose purpose,
            String date,
            String time,
            int year,
            Organ organ
    ) {
        return Log.builder()
                .name(request.getName())
                .age(request.getAge())
                .phone(request.getPhone())
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
