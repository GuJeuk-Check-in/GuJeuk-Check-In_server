package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryAllMonthLogCountResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAllMonthLogCountService {
    private final LogRepository logRepository;

    @Transactional(readOnly = true)
    public QueryAllMonthLogCountResponse execute(String year) {
        List<Integer> monthCount = logRepository.findMonthlyCounts(year);

        return QueryAllMonthLogCountResponse.of(
            monthCount.get(0),
            monthCount.get(1),
            monthCount.get(2),
            monthCount.get(3),
            monthCount.get(4),
            monthCount.get(5),
            monthCount.get(6),
            monthCount.get(7),
            monthCount.get(8),
            monthCount.get(9),
            monthCount.get(10),
            monthCount.get(11)
        );
    }
}
