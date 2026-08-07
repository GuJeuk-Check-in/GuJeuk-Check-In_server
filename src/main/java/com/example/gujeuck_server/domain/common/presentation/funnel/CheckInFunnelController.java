package com.example.gujeuck_server.domain.common.presentation.funnel;

import com.example.gujeuck_server.domain.common.presentation.funnel.dto.request.CheckInFunnelEventsRequest;
import com.example.gujeuck_server.domain.common.service.funnel.CreateCheckInFunnelEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common/analytics")
@RequiredArgsConstructor
public class CheckInFunnelController {
    private final CreateCheckInFunnelEventService createCheckInFunnelEventService;

    @PostMapping("/check-in-funnel")
    public ResponseEntity<Void> createCheckInFunnelEvents(@RequestBody @Valid CheckInFunnelEventsRequest request) {
        createCheckInFunnelEventService.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
