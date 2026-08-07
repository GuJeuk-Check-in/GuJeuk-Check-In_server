package com.example.gujeuck_server.domain.common.presentation.funnel;

import com.example.gujeuck_server.domain.common.domain.funnel.enums.VisitCountBucket;
import com.example.gujeuck_server.domain.common.service.funnel.CreateCheckInFunnelEventService;
import com.example.gujeuck_server.domain.common.presentation.funnel.dto.response.CheckInFunnelEventResponse;
import com.example.gujeuck_server.domain.common.service.funnel.QueryCheckInFunnelEventService;
import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.global.error.GlobalExceptionHandler;
import com.example.gujeuck_server.global.security.auth.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CheckInFunnelControllerTest {

    @Mock
    private CreateCheckInFunnelEventService createCheckInFunnelEventService;

    @Mock
    private QueryCheckInFunnelEventService queryCheckInFunnelEventService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc = MockMvcBuilders.standaloneSetup(new CheckInFunnelController(
                        createCheckInFunnelEventService,
                        queryCheckInFunnelEventService
                ))
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(
                        new AuthenticationPrincipalArgumentResolver(),
                        new PageableHandlerMethodArgumentResolver()
                )
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(organ());
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities())
        );
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
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

        verify(createCheckInFunnelEventService).execute(eq(1L), any());
    }

    @Test
    void 체크인_퍼널_이벤트_원본을_최신순으로_조회한다() throws Exception {
        CheckInFunnelEventResponse response = new CheckInFunnelEventResponse(
                1L,
                "11111111-1111-1111-1111-111111111111",
                "22222222-2222-2222-2222-222222222222",
                "check_in_completed_view",
                LocalDateTime.of(2026, 8, 5, 19, 17, 30),
                120000L,
                123L,
                Age.AGE_14_16,
                "친친마루(게임, 독서 등)",
                null,
                true,
                4L,
                VisitCountBucket.RETURNING_4_9,
                LocalDateTime.of(2026, 8, 5, 19, 18, 0)
        );

        when(queryCheckInFunnelEventService.execute(any()))
                .thenReturn(new SliceImpl<>(List.of(response), PageRequest.of(0, 30), false));

        mockMvc.perform(get("/common/analytics/check-in-funnel/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].clientEventId").value("11111111-1111-1111-1111-111111111111"))
                .andExpect(jsonPath("$.content[0].eventName").value("check_in_completed_view"))
                .andExpect(jsonPath("$.content[0].occurredAt").value("2026-08-05T19:17:30"))
                .andExpect(jsonPath("$.content[0].isExistingUser").value(true))
                .andExpect(jsonPath("$.content[0].visitCountBucket").value("RETURNING_4_9"));

        verify(queryCheckInFunnelEventService).execute(any());
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

        verify(createCheckInFunnelEventService).execute(eq(1L), any());
    }

    private Organ organ() {
        Organ organ = Organ.builder()
                .organName("구즉청소년문화의집")
                .password("password")
                .build();
        ReflectionTestUtils.setField(organ, "id", 1L);
        return organ;
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
