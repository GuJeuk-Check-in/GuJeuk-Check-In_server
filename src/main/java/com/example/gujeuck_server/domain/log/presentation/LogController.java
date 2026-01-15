package com.example.gujeuck_server.domain.log.presentation;

import com.example.gujeuck_server.domain.log.presentation.dto.request.LogRequest;
import com.example.gujeuck_server.domain.log.presentation.dto.response.LogSliceWithTotalResponse;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryLogDetailResponse;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryLogListResponse;
import com.example.gujeuck_server.domain.log.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/log")
@RestController
@RequiredArgsConstructor
public class LogController {
    private final CreateLogService createUseListService;
    private final QueryLogListService queryLogListService;
    private final DeleteLogService deleteLogService;
    private final UpdateLogService updateLogService;
    private final QueryLogDetailService queryLogDetailService;
    private final QueryLogListByDateService queryLogListByDateService;
    @PostMapping
    public void createLog(@RequestBody @Valid LogRequest request) {
        createUseListService.execute(request);
    }

    @DeleteMapping("/{log-id}")
    public void deleteLog(@PathVariable("log-id") Long logId) {
        deleteLogService.execute(logId);
    }

    @PatchMapping("/{log-id}")
    public void updateLog(@PathVariable("log-id") Long logId, @RequestBody @Valid LogRequest request) {
        updateLogService.execute(logId, request);
    }

    @GetMapping
    public Slice<QueryLogListResponse> queryLogList(
            @PageableDefault(size = 30, sort = {"visitDate", "id"}, direction = Sort.Direction.DESC)
            Pageable pageable) {
        return queryLogListService.execute(pageable);
    }

    @GetMapping("/{log-id}")
    public QueryLogDetailResponse queryLogDetail(@PathVariable("log-id") Long logId) {
        return queryLogDetailService.execute(logId);
    }

    @GetMapping("/date/{date}")
    public LogSliceWithTotalResponse<QueryLogListResponse> queryLogListByDate(@PathVariable("date") String date, Pageable pageable) {
        return queryLogListByDateService.queryLogListByResidence(date, pageable);
    }
}
