package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.exception.DuplicateLogException;
import com.example.gujeuck_server.domain.log.facade.LogFacade;
import com.example.gujeuck_server.domain.log.presentation.dto.request.LogRequest;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.global.utility.DateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateLogService {
    private final LogFacade logFacade;
    private final LogRepository logRepository;
    private final PurposeFacade purposeFacade;

    @Transactional
    public void execute(Long organId, Long logId, LogRequest request) {
        Log log = logFacade.getLogByIdAndOrganId(logId, organId);
        String name = request.getName().trim();
        Purpose purpose = purposeFacade.getPurpose(organId, request.getPurpose().trim());
        String visitDate = DateFormatter.toVisitDate(request.getVisitTime());
        String visitTime = DateFormatter.toVisitTime(request.getVisitTime());

        validateDuplicateLog(
                organId,
                name,
                request.getAge(),
                purpose.getPurposeName(),
                visitDate,
                visitTime,
                logId
        );

        log.updateLog(
                name,
                request.getAge(),
                request.getPhone(),
                request.getMaleCount(),
                request.getFemaleCount(),
                purpose.getPurposeName(),
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
}
