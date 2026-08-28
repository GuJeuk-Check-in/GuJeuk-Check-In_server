package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.facade.LogFacade;
import com.example.gujeuck_server.domain.log.exception.InvalidLogDateException;
import com.example.gujeuck_server.domain.log.presentation.dto.request.LogRequest;
import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateLogService {

    private final LogRepository logRepository;
    private final LogFacade logFacade;
    private final PurposeFacade purposeFacade;

    @Transactional
    public void execute(Organ organ, LogRequest request) {
        String name = request.name().trim();
        String purposeName = request.purpose().trim();
        String visitDate = request.visitDate();
        String visitTime = request.visitTime();
        int year = extractYear(visitDate);

        Purpose purpose = purposeFacade.getPurpose(organ.getId(), purposeName);

        // 생성이라 제외할 기록이 없다.
        logFacade.validateNotDuplicated(
                organ.getId(), name, request.age(), purpose.getPurposeName(), visitDate, visitTime, null);

        logRepository.save(createUseLog(request, name, purpose, visitDate, visitTime, year, organ));
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
                .age(request.age())
                .phone(request.phone())
                .maleCount(request.maleCount())
                .femaleCount(request.femaleCount())
                .purpose(purpose.getPurposeName())
                .visitTime(time)
                .visitDate(date)
                .year(year)
                .privacyAgreed(request.privacyAgreed())
                .organ(organ)
                .build();
    }

    private int extractYear(String visitDate) {
        if (visitDate == null || visitDate.length() < 4) {
            throw InvalidLogDateException.EXCEPTION;
        }

        try {
            return Integer.parseInt(visitDate.substring(0, 4));
        } catch (NumberFormatException e) {
            throw InvalidLogDateException.EXCEPTION;
        }
    }
}
