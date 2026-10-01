package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryAllMonthLogCountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryAllMonthLogCountService {
    private final LogRepository logRepository;

    @Transactional(readOnly = true)
    public QueryAllMonthLogCountResponse execute(String year) {
        return QueryAllMonthLogCountResponse.of(logRepository.findMonthlyCounts(year));
    }
}
