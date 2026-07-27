package com.example.gujeuck_server.domain.common.service;

import com.example.gujeuck_server.domain.common.presentation.dto.response.ReadyHealthResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HealthCheckServiceTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @InjectMocks
    private HealthCheckService healthCheckService;

    @Test
    void DB_연결이_가능하면_ready는_UP이다() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.isValid(1)).thenReturn(true);

        ReadyHealthResponse response = healthCheckService.ready();

        assertThat(response.status()).isEqualTo("UP");
        assertThat(response.db()).isEqualTo("UP");
        assertThat(response.hasUpStatus()).isTrue();
    }

    @Test
    void DB_연결이_유효하지_않으면_ready는_DOWN이다() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.isValid(1)).thenReturn(false);

        ReadyHealthResponse response = healthCheckService.ready();

        assertThat(response.status()).isEqualTo("DOWN");
        assertThat(response.db()).isEqualTo("DOWN");
        assertThat(response.hasUpStatus()).isFalse();
    }

    @Test
    void DB_연결_확인에서_예외가_발생하면_ready는_DOWN이다() throws SQLException {
        when(dataSource.getConnection()).thenThrow(new SQLException("database unavailable"));

        ReadyHealthResponse response = healthCheckService.ready();

        assertThat(response.status()).isEqualTo("DOWN");
        assertThat(response.db()).isEqualTo("DOWN");
        assertThat(response.hasUpStatus()).isFalse();
    }
}
