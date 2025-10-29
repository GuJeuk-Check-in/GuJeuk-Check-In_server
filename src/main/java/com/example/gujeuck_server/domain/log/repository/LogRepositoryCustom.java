package com.example.gujeuck_server.domain.log.repository;

import com.example.gujeuck_server.domain.log.dto.response.LogResponse;
import com.example.gujeuck_server.domain.log.entity.Log;

import java.util.List;
import java.util.Optional;

public interface LogRepositoryCustom {
    List<LogResponse> findAllByCurrentMonth();

    Optional<Log> findByUserIdAndVisitTime(String userId, String visitDate, String visitTime);
}
