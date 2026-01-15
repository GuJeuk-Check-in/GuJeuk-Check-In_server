package com.example.gujeuck_server.domain.log.facade;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.exception.LogNotFountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogFacade {
    private final LogRepository logRepository;

    public Log getLogById(Long logId) {

        return logRepository.findById(logId)
                .orElseThrow(() -> LogNotFountException.EXCEPTION);
    }
}
