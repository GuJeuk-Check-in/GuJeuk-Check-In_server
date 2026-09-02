package com.example.gujeuck_server.domain.organ.service;

import com.example.gujeuck_server.domain.log.presentation.dto.response.MonthlyOperationCount;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.SystemUsageOneResponse;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.SystemUsageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemUsageService {
    private static final int CAPACITY = 75;

    private final LogRepository logRepository;

    @Transactional(readOnly = true)
    public SystemUsageResponse execute(Long organId, int year) {
        List<SystemUsageOneResponse> months = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            String yearMonth = String.format("%d년%02d월", year, month);
            MonthlyOperationCount count = logRepository.findMonthlyOperationCount(organId, yearMonth);

            months.add(toResponse(count));
        }

        SystemUsageOneResponse total = calculateTotal(months);

        return SystemUsageResponse.of(total, months);
    }

    private SystemUsageOneResponse toResponse(MonthlyOperationCount count) {
        if (count.operatingDays() == 0) {
            return SystemUsageOneResponse.empty();
        }

        double rate = (double) count.totalVisitors() / (CAPACITY * count.operatingDays()) * 100;

        return SystemUsageOneResponse.of((int) count.operatingDays(), round(rate));
    }

    private SystemUsageOneResponse calculateTotal(List<SystemUsageOneResponse> months) {
        int totalOpDate = 0;
        double rateSum = 0;
        int presentMonthCount = 0;

        for (SystemUsageOneResponse month : months) {
            if (month.opDate() == null) {
                continue;
            }

            totalOpDate += month.opDate();
            rateSum += month.avgRate();
            presentMonthCount++;
        }

        if (presentMonthCount == 0) {
            return SystemUsageOneResponse.empty();
        }

        return SystemUsageOneResponse.of(totalOpDate, round(rateSum / presentMonthCount));
    }

    private double round(double value) {
        return Math.round(value * 10) / 10.0;
    }
}
