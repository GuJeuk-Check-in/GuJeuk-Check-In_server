package com.example.gujeuck_server.domain.log.facade;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.exception.DuplicateLogException;
import com.example.gujeuck_server.domain.user.domain.enums.Age;

import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.exception.LogNotFountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogFacade {
    private final LogRepository logRepository;

    public Log getLogByIdAndOrganId(Long logId, Long organId) {
        return logRepository.findByIdAndOrganId(logId, organId)
                .orElseThrow(() -> LogNotFountException.EXCEPTION);
    }

    public void validateNotDuplicated(
            Long organId,
            String name,
            Age age,
            String purpose,
            String visitDate,
            String visitTime,
            Long excludedId
    ) {
        boolean duplicated = excludedId == null
                ? logRepository.existsByOrganIdAndNameAndAgeAndPurposeAndVisitDateAndVisitTime(
                        organId, name, age, purpose, visitDate, visitTime)
                : logRepository.existsByOrganIdAndNameAndAgeAndPurposeAndVisitDateAndVisitTimeAndIdNot(
                        organId, name, age, purpose, visitDate, visitTime, excludedId);

        if (duplicated) {
            throw DuplicateLogException.EXCEPTION;
        }
    }
}
