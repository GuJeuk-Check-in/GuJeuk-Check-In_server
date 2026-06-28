package com.example.gujeuck_server.domain.log.domain.repository;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.VisitStatisticsCount;
import com.example.gujeuck_server.domain.log.presentation.dto.response.LogExcelResponse;

import java.util.List;
import java.util.Optional;

public interface LogRepositoryCustom {
    List<LogExcelResponse> findAllByCurrentMonth();

    Optional<Log> findByUserIdAndVisitTime(Long userId, String visitDate, String visitTime);

    long countByYearMonth(Long organId, String yearMonth);

    List<Log> findAllByOrganIdAndVisitDateStartingWithOrderByDateTime(Long organId, String visitDate);

    VisitStatisticsCount summarizeVisits(Long organId, String startVisitDate, String endVisitDate);
}
