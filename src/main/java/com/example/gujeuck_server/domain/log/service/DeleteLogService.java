package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.log.exception.LogNotFountException;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteLogService {
    private final LogRepository logRepository;
    private final AdminFacade adminFacade;

    @Transactional
    public void execute(Long logId) {
        adminFacade.currentUser();

        Log log = logRepository.findById(logId).orElseThrow(
                () -> LogNotFountException.EXCEPTION);

        logRepository.delete(log);
    }
}
