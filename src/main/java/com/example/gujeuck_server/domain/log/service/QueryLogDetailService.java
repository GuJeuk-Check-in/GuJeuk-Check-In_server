package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.admin.presentation.dto.response.LogResponse;
import com.example.gujeuck_server.domain.log.exception.LogNotFountException;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryLogDetailService {
    private final LogRepository logRepository;
    private final AdminFacade adminFacade;

    public LogResponse readOneUseList(Long id) {
        adminFacade.currentUser();

        Log log = logRepository.findById(id).orElseThrow(
                () -> LogNotFountException.EXCEPTION
        );

        return new LogResponse(log);
    }
}