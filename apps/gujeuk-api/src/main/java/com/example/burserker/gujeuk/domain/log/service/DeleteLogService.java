package com.example.burserker.gujeuk.domain.log.service;

import com.example.burserker.gujeuk.domain.log.domain.repository.LogRepository;
import com.example.burserker.gujeuk.domain.log.facade.LogFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteLogService {
    private final LogFacade logFacade;
    private final LogRepository logRepository;

    @Transactional
    public void execute(Long organId, Long logId) {
        logRepository.delete(logFacade.getLogByIdAndOrganId(logId, organId));
    }
}
