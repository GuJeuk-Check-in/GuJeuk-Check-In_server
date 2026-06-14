package com.example.gujeuck_server.domain.organ.service;

import com.example.gujeuck_server.domain.log.domain.VisitStatisticsCount;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.exception.InvalidLogDateException;
import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.VisitStatisticsResponse;
import com.example.gujeuck_server.global.utility.DateFormatter;
import com.example.gujeuck_server.global.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class QueryVisitStatisticsService {

    private final LogRepository logRepository;
    private final OrganFacade organFacade;

    @Transactional(readOnly = true)
    public VisitStatisticsResponse execute(int year, int month) {
        YearMonth selectedMonth = resolveYearMonth(year, month);
        Organ organ = organFacade.currentOrgan();

        VisitStatisticsCount monthly = queryStatistics(
                organ.getId(),
                selectedMonth.atDay(1),
                selectedMonth.atEndOfMonth()
        );

        VisitStatisticsCount cumulative = queryStatistics(
                organ.getId(),
                selectedMonth.withMonth(1).atDay(1),
                selectedMonth.atEndOfMonth()
        );

        return VisitStatisticsResponse.of(year, month, cumulative, monthly);
    }

    private VisitStatisticsCount queryStatistics(
            Long organId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return logRepository.summarizeVisits(
                organId,
                DateFormatter.LocalDateForm(startDate),
                DateFormatter.LocalDateForm(endDate)
        );
    }

    private YearMonth resolveYearMonth(int year, int month) {
        try {
            if (year < 1) {
                throw InvalidLogDateException.EXCEPTION;
            }

            YearMonth selectedMonth = YearMonth.of(year, month);
            YearMonth currentMonth = YearMonth.from(TimeProvider.nowZoned());

            if (selectedMonth.isAfter(currentMonth)) {
                throw InvalidLogDateException.EXCEPTION;
            }

            return selectedMonth;
        } catch (DateTimeException exception) {
            throw InvalidLogDateException.EXCEPTION;
        }
    }
}
