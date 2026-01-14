package com.example.gujeuck_server.domain.log.domain.repository;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.presentation.dto.response.LogExcelResponse;

import java.util.List;
import java.util.Optional;

public interface LogRepositoryCustom {
    List<LogExcelResponse> findAllByCurrentMonth();

    Optional<Log> findByUserIdAndVisitTime(String userId, String visitDate, String visitTime);
}
