package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.exception.LogAccessDeniedException;
import com.example.gujeuck_server.domain.log.facade.LogFacade;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryLogDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryLogDetailService {
    private final OrganFacade organFacade;
    private final LogFacade logFacade;

    @Transactional(readOnly = true)
    public QueryLogDetailResponse execute(Long logId) {
        Organ organ = organFacade.currentOrgan();

        Log log = logFacade.getLogById(logId);

        if (!log.getOrgan().getId().equals(organ.getId())) {
            throw LogAccessDeniedException.EXCEPTION;
        }

        return new QueryLogDetailResponse(log);
    }
}