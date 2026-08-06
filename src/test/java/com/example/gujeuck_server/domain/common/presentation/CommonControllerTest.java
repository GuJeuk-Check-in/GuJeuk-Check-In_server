package com.example.gujeuck_server.domain.common.presentation;

import com.example.gujeuck_server.domain.common.analytics.service.CreateCheckInFunnelEventService;
import com.example.gujeuck_server.domain.common.presentation.dto.response.ReadyHealthResponse;
import com.example.gujeuck_server.domain.common.service.QueryHealthCheckService;
import com.example.gujeuck_server.global.error.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CommonControllerTest {

    @Mock
    private QueryHealthCheckService queryHealthCheckService;

    @Mock
    private CreateCheckInFunnelEventService createCheckInFunnelEventService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

        mockMvc = MockMvcBuilders.standaloneSetup(new CommonController(queryHealthCheckService, createCheckInFunnelEventService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
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

    @Test
    void 체크인_퍼널_이벤트를_저장하면_201과_빈_body를_반환한다() throws Exception {
        mockMvc.perform(post("/common/analytics/check-in-funnel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "events": [
                                    {
                                      "clientEventId": "11111111-1111-1111-1111-111111111111",
                                      "sessionId": "22222222-2222-2222-2222-222222222222",
                                      "eventName": "check_in_completed_view",
                                      "occurredAt": "2026-08-05T10:17:30.000Z",
                                      "elapsedMsFromStart": 120000,
                                      "userId": 123,
                                      "ageGroup": "AGE_14_16",
                                      "purpose": "친친마루(게임, 독서 등)",
                                      "failureReason": null
                                    }
                                  ]
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(content().string(""));

        verify(createCheckInFunnelEventService).execute(any());
    }

    @Test
    void 체크인_퍼널_이벤트의_purpose와_failureReason은_255자까지_허용한다() throws Exception {
        String maxLengthValue = "a".repeat(255);

        mockMvc.perform(post("/common/analytics/check-in-funnel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "events": [
                                    {
                                      "clientEventId": "11111111-1111-1111-1111-111111111111",
                                      "sessionId": "22222222-2222-2222-2222-222222222222",
                                      "eventName": "check_in_api_failed",
                                      "occurredAt": "2026-08-05T10:17:30.000Z",
                                      "elapsedMsFromStart": 120000,
                                      "purpose": "%s",
                                      "failureReason": "%s"
                                    }
                                  ]
                                }
                                """.formatted(maxLengthValue, maxLengthValue)))
                .andExpect(status().isCreated())
                .andExpect(content().string(""));

        verify(createCheckInFunnelEventService).execute(any());
    }

    @Test
    void 체크인_퍼널_이벤트의_purpose가_256자이면_400이다() throws Exception {
        String tooLongPurpose = "a".repeat(256);

        mockMvc.perform(post("/common/analytics/check-in-funnel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "events": [
                                    {
                                      "clientEventId": "11111111-1111-1111-1111-111111111111",
                                      "sessionId": "22222222-2222-2222-2222-222222222222",
                                      "eventName": "purpose_selected",
                                      "occurredAt": "2026-08-05T10:17:30.000Z",
                                      "elapsedMsFromStart": 120000,
                                      "purpose": "%s"
                                    }
                                  ]
                                }
                                """.formatted(tooLongPurpose)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(createCheckInFunnelEventService);
    }

    @Test
    void 체크인_퍼널_이벤트의_failureReason이_256자이면_400이다() throws Exception {
        String tooLongFailureReason = "a".repeat(256);

        mockMvc.perform(post("/common/analytics/check-in-funnel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "events": [
                                    {
                                      "clientEventId": "11111111-1111-1111-1111-111111111111",
                                      "sessionId": "22222222-2222-2222-2222-222222222222",
                                      "eventName": "check_in_api_failed",
                                      "occurredAt": "2026-08-05T10:17:30.000Z",
                                      "elapsedMsFromStart": 120000,
                                      "failureReason": "%s"
                                    }
                                  ]
                                }
                                """.formatted(tooLongFailureReason)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(createCheckInFunnelEventService);
    }

    @Test
    void 체크인_퍼널_이벤트_목록이_비어있으면_400이다() throws Exception {
        mockMvc.perform(post("/common/analytics/check-in-funnel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "events": []
                                }
                                """))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(createCheckInFunnelEventService);
    }

    @Test
    void 체크인_퍼널_이벤트_enum이_잘못되면_400이다() throws Exception {
        mockMvc.perform(post("/common/analytics/check-in-funnel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "events": [
                                    {
                                      "clientEventId": "11111111-1111-1111-1111-111111111111",
                                      "sessionId": "22222222-2222-2222-2222-222222222222",
                                      "eventName": "unknown_event",
                                      "occurredAt": "2026-08-05T10:17:30.000Z",
                                      "elapsedMsFromStart": 120000
                                    }
                                  ]
                                }
                                """))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(createCheckInFunnelEventService);
    }

    @Test
    void 체크인_퍼널_이벤트_시간이_ISO_datetime이_아니면_400이다() throws Exception {
        mockMvc.perform(post("/common/analytics/check-in-funnel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "events": [
                                    {
                                      "clientEventId": "11111111-1111-1111-1111-111111111111",
                                      "sessionId": "22222222-2222-2222-2222-222222222222",
                                      "eventName": "check_in_page_view",
                                      "occurredAt": "2026-08-05T10:17:30.000",
                                      "elapsedMsFromStart": 0
                                    }
                                  ]
                                }
                                """))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(createCheckInFunnelEventService);
    }
}
