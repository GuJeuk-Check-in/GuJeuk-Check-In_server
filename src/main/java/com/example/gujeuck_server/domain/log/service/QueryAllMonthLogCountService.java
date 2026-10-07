package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.presentation.dto.response.MonthlyLogCountResponse;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryAllMonthLogCountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAllMonthLogCountService {
    private final LogRepository logRepository;

    @Transactional(readOnly = true)
    public QueryAllMonthLogCountResponse execute(String year) {
        List<MonthlyLogCountResponse> results = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            results.add(new MonthlyLogCountResponse(i, 0));
        }

        List<MonthlyLogCountResponse> monthCounts =
            logRepository.findMonthlyCounts(year);

        for (MonthlyLogCountResponse result : monthCounts) {
            int monthCount = result.month();
            int index = monthCount - 1;

            results.set(index, result);
        }

        return QueryAllMonthLogCountResponse.of(results);
    }
}
