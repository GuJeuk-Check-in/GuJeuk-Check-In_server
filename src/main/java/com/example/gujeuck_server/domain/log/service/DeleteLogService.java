package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.exception.LogAccessDeniedException;
import com.example.gujeuck_server.domain.log.facade.LogFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteLogService {
    private final LogFacade logFacade;
    private final LogRepository logRepository;
    private final OrganFacade organFacade;

    @Transactional
    public void execute(Long logId) {
        Organ organ = organFacade.currentUser();

        Log log = logFacade.getLogById(logId);

        if (!log.getOrgan().getId().equals(organ.getId())) {
            throw LogAccessDeniedException.EXCEPTION;
        }

        logRepository.delete(log);
    }
}
