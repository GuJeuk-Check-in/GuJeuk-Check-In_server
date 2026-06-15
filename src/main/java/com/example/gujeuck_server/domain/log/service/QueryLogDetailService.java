package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.facade.LogFacade;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryLogDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryLogDetailService {
    private final LogFacade logFacade;

    @Transactional(readOnly = true)
    public QueryLogDetailResponse execute(Long organId, Long logId) {
        Log log = logFacade.getLogByIdAndOrganId(logId, organId);
        return new QueryLogDetailResponse(log);
    }
}
