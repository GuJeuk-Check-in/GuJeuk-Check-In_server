package com.example.gujeuck_server.domain.common.presentation;

import com.example.gujeuck_server.domain.common.analytics.presentation.dto.request.CheckInFunnelEventsRequest;
import com.example.gujeuck_server.domain.common.analytics.service.CreateCheckInFunnelEventService;
import com.example.gujeuck_server.domain.common.presentation.dto.response.ReadyHealthResponse;
import com.example.gujeuck_server.domain.common.service.QueryHealthCheckService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonController {
    private final QueryHealthCheckService queryHealthCheckService;
    private final CreateCheckInFunnelEventService createCheckInFunnelEventService;

    @GetMapping("/health/ready")
    public ResponseEntity<ReadyHealthResponse> ready() {
        ReadyHealthResponse response = queryHealthCheckService.ready();
        HttpStatus status = response.hasUpStatus() ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;

        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("/analytics/check-in-funnel")
    public ResponseEntity<Void> createCheckInFunnelEvents(@RequestBody @Valid CheckInFunnelEventsRequest request) {
        createCheckInFunnelEventService.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
