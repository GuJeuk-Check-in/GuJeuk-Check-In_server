package com.example.gujeuck_server.domain.log.presentation;

import com.example.gujeuck_server.domain.log.presentation.dto.request.LogRequest;
import com.example.gujeuck_server.domain.log.presentation.dto.response.LogSliceWithTotalResponse;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryLogDetailResponse;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryLogListResponse;
import com.example.gujeuck_server.domain.log.service.CreateLogService;
import com.example.gujeuck_server.domain.log.service.DeleteLogService;
import com.example.gujeuck_server.domain.log.service.QueryLogDetailService;
import com.example.gujeuck_server.domain.log.service.QueryLogListByDateService;
import com.example.gujeuck_server.domain.log.service.QueryLogListService;
import com.example.gujeuck_server.domain.log.service.UpdateLogService;
import com.example.gujeuck_server.global.security.auth.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/log")
@RestController
@RequiredArgsConstructor
public class LogController {
    private final CreateLogService createLogService;
    private final QueryLogListService queryLogListService;
    private final DeleteLogService deleteLogService;
    private final UpdateLogService updateLogService;
    private final QueryLogDetailService queryLogDetailService;
    private final QueryLogListByDateService queryLogListByDateService;

    @PostMapping
    public void createLog(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid LogRequest request
    ) {
        createLogService.execute(userDetails.organ(), request);
    }

    @DeleteMapping("/{log-id}")
    public void deleteLog(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("log-id") Long logId
    ) {
        deleteLogService.execute(userDetails.organ().getId(), logId);
    }

    @PatchMapping("/{log-id}")
    public void updateLog(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("log-id") Long logId,
            @RequestBody @Valid LogRequest request
    ) {
        updateLogService.execute(userDetails.organ().getId(), logId, request);
    }

    @GetMapping
    public Slice<QueryLogListResponse> queryLogList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PageableDefault(size = 30, sort = {"visitDate", "id"}, direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return queryLogListService.execute(userDetails.organ().getId(), pageable);
    }

    @GetMapping("/{log-id}")
    public QueryLogDetailResponse queryLogDetail(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("log-id") Long logId
    ) {
        return queryLogDetailService.execute(userDetails.organ().getId(), logId);
    }

    @GetMapping("/date/{date}")
    public LogSliceWithTotalResponse queryLogListByDate(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("date") String date,
            Pageable pageable
    ) {
        return queryLogListByDateService.queryLogListByResidence(userDetails.organ().getId(), date, pageable);
    }
}
