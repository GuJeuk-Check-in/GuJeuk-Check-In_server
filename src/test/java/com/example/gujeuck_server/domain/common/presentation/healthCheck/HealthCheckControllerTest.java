package com.example.gujeuck_server.domain.common.presentation.healthCheck;

import com.example.gujeuck_server.domain.common.presentation.healthCheck.dto.response.ReadyHealthResponse;
import com.example.gujeuck_server.domain.common.service.healthCheck.QueryHealthCheckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class HealthCheckControllerTest {

    @Mock
    private QueryHealthCheckService queryHealthCheckService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new HealthCheckController(queryHealthCheckService))
                .build();
    }

    @Test
    void ready가_UP이면_200으로_반환한다() throws Exception {
        when(queryHealthCheckService.ready()).thenReturn(ReadyHealthResponse.up());

        mockMvc.perform(get("/common/health/ready"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.db").value("UP"))
                .andExpect(jsonPath("$.up").doesNotExist());
    }

    @Test
    void ready가_DOWN이면_503으로_반환한다() throws Exception {
        when(queryHealthCheckService.ready()).thenReturn(ReadyHealthResponse.down());

        mockMvc.perform(get("/common/health/ready"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.status").value("DOWN"))
                .andExpect(jsonPath("$.db").value("DOWN"))
                .andExpect(jsonPath("$.up").doesNotExist());
    }
}
