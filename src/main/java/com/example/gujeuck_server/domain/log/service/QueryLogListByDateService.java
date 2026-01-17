package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.presentation.dto.response.LogSliceWithTotalResponse;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryLogListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryLogListByDateService {
    private final LogRepository logRepository;
    private final AdminFacade  adminFacade;

    public LogSliceWithTotalResponse queryLogListByResidence(String yearMonth, Pageable p) {
        adminFacade.currentUser();

        Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        String date = toYearMonthPrefix(yearMonth);

        Slice<QueryLogListResponse> slice = logRepository.findByVisitDateStartingWith(date, pageable)
                .map(QueryLogListResponse::from);

        long total = logRepository.countByYearMonth(date);

        return new LogSliceWithTotalResponse(total, slice);
    }

    private String toYearMonthPrefix(String yearMonth) {
        String[] parts = yearMonth.split("-");

        String year = parts[0];
        String month = parts[1];

        return year + "년" + month + "월";
    }

}
