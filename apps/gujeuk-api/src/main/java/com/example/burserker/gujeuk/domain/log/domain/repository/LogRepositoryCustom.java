package com.example.burserker.gujeuk.domain.log.domain.repository;

import com.example.burserker.gujeuk.domain.log.domain.Log;
import com.example.burserker.gujeuk.domain.log.presentation.dto.response.MonthlyLogCountResponse;
import com.example.burserker.gujeuk.domain.organ.presentation.dto.response.organ.MonthlyOperationCount;
import com.example.burserker.gujeuk.domain.organ.presentation.dto.response.organ.VisitStatisticsCount;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LogRepositoryCustom {
    Optional<Log> findByUserIdAndVisitAt(Long userId, LocalDateTime visitAt, String purpose);

    long countByYearMonth(Long organId, String yearMonth);

    List<Log> findAllByOrganIdAndVisitDateStartingWithOrderByDateTime(Long organId, String visitDate);

    VisitStatisticsCount summarizeVisits(Long organId, String startVisitDate, String endVisitDate);

    MonthlyOperationCount findMonthlyOperationCount(Long organId, String yearMonth);

    List<MonthlyLogCountResponse> findMonthlyCounts(String year);
}
