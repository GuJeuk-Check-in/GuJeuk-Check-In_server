package com.example.gujeuck_server.domain.common.service;

import com.example.gujeuck_server.domain.common.presentation.dto.response.ReadyHealthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
@RequiredArgsConstructor
public class QueryHealthCheckService {
    private final DataSource dataSource;

    public ReadyHealthResponse ready() {
        if (isDatabaseUp()) {
            return ReadyHealthResponse.up();
        }

        return ReadyHealthResponse.down();
    }

    private boolean isDatabaseUp() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(1);
        } catch (Exception exception) {
            return false;
        }
    }
}
