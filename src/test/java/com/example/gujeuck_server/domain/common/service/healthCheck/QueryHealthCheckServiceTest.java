package com.example.gujeuck_server.domain.common.service.healthCheck;

import com.example.gujeuck_server.domain.common.presentation.healthCheck.dto.response.ReadyHealthResponse;
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
class QueryHealthCheckServiceTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @InjectMocks
    private QueryHealthCheckService queryHealthCheckService;

    @Test
    void DB_연결이_가능하면_ready는_UP이다() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.isValid(1)).thenReturn(true);

        ReadyHealthResponse response = queryHealthCheckService.ready();

        assertThat(response.status()).isEqualTo("UP");
        assertThat(response.db()).isEqualTo("UP");
        assertThat(response.hasUpStatus()).isTrue();
    }

    @Test
    void DB_연결이_유효하지_않으면_ready는_DOWN이다() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.isValid(1)).thenReturn(false);

        ReadyHealthResponse response = queryHealthCheckService.ready();

        assertThat(response.status()).isEqualTo("DOWN");
        assertThat(response.db()).isEqualTo("DOWN");
        assertThat(response.hasUpStatus()).isFalse();
    }

    @Test
    void DB_연결_확인에서_예외가_발생하면_ready는_DOWN이다() throws SQLException {
        when(dataSource.getConnection()).thenThrow(new SQLException("database unavailable"));

        ReadyHealthResponse response = queryHealthCheckService.ready();

        assertThat(response.status()).isEqualTo("DOWN");
        assertThat(response.db()).isEqualTo("DOWN");
        assertThat(response.hasUpStatus()).isFalse();
    }
}
