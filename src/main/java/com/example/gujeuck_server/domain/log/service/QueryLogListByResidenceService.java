package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.presentation.dto.request.QueryLogListByResidenceRequest;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryLogListResponse;
import com.example.gujeuck_server.domain.log.presentation.dto.response.SliceWithTotalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryLogListByResidenceService {
    private final LogRepository logRepository;

    public SliceWithTotalResponse<QueryLogListResponse> QueryLogListByResidence(QueryLogListByResidenceRequest queryLogListByResidenceRequest) {
        
    }
}
