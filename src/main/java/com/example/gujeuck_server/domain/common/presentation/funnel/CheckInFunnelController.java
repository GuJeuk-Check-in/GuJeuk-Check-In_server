package com.example.gujeuck_server.domain.common.presentation.funnel;

import com.example.gujeuck_server.domain.common.presentation.funnel.dto.request.CheckInFunnelEventsRequest;
import com.example.gujeuck_server.domain.common.presentation.funnel.dto.response.CheckInFunnelEventResponse;
import com.example.gujeuck_server.domain.common.service.funnel.CreateCheckInFunnelEventService;
import com.example.gujeuck_server.domain.common.service.funnel.QueryCheckInFunnelEventService;
import com.example.gujeuck_server.global.security.auth.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common/analytics")
@RequiredArgsConstructor
public class CheckInFunnelController {
    private final CreateCheckInFunnelEventService createCheckInFunnelEventService;
    private final QueryCheckInFunnelEventService queryCheckInFunnelEventService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/check-in-funnel")
    public void createCheckInFunnelEvents(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid CheckInFunnelEventsRequest request
    ) {
        createCheckInFunnelEventService.execute(userDetails.organ().getId(), request);
    }

    @GetMapping("/check-in-funnel/events")
    public Slice<CheckInFunnelEventResponse> queryCheckInFunnelEvents(
            @PageableDefault(size = 30, sort = {"createdAt", "id"}, direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return queryCheckInFunnelEventService.execute(pageable);
    }
}
