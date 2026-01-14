package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.facade.LogFacade;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryLogDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryLogDetailService {
    private final AdminFacade adminFacade;
    private final LogFacade logFacade;

    @Transactional(readOnly = true)
    public QueryLogDetailResponse execute(Long logId) {
        adminFacade.currentUser();

        Log log = logFacade.getLogById(logId);

        return new QueryLogDetailResponse(log);
    }
}