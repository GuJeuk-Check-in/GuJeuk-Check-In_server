package com.example.gujeuck_server.domain.log.repository;

import com.example.gujeuck_server.domain.log.dto.response.LogResponse;

import java.util.List;

public interface LogRepositoryCustom {
    List<LogResponse> findAllByCurrentMonth();
}
