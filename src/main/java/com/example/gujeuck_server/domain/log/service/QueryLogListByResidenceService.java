package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.presentation.dto.request.QueryLogListByResidenceRequest;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryLogListResponse;
import com.example.gujeuck_server.domain.log.presentation.dto.response.SliceWithTotalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryLogListByResidenceService {
    private final LogRepository logRepository;

    public SliceWithTotalResponse<QueryLogListResponse> QueryLogListByResidence(QueryLogListByResidenceRequest queryLogListByResidenceRequest) {
        Slice<QueryLogListResponse> logs = logRepository.findByYearAndMonth(
                queryLogListByResidenceRequest.getYear(),
                queryLogListByResidenceRequest.getMonth()
        );

        long total = logRepository.countByYearAndMonth(
                queryLogListByResidenceRequest.getYear(),
                queryLogListByResidenceRequest.getMonth()
        );

        return new SliceWithTotalResponse<>(total, logs);
    }
}
