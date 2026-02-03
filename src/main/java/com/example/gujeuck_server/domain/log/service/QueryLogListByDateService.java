package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.exception.InvalidLogDateException;
import com.example.gujeuck_server.domain.log.presentation.dto.response.LogSliceWithTotalResponse;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryLogListResponse;
import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class QueryLogListByDateService {
    private final LogRepository logRepository;
    private final OrganFacade organFacade;

    public LogSliceWithTotalResponse queryLogListByResidence(String yearMonth, Pageable p) {
        Organ organ = organFacade.currentOrgan();

        Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        String date = toYearMonthPrefix(yearMonth);

        Slice<QueryLogListResponse> slice = logRepository.findAllByOrganIdAndVisitDateStartingWith(organ.getId(), date, pageable)
                .map(QueryLogListResponse::from);

        long total = logRepository.countByYearMonth(date);

        return LogSliceWithTotalResponse.builder()
                .slice(slice)
                .totalCount(total)
                .build();
    }

    private String toYearMonthPrefix(String yearMonth) {
        String[] parts = yearMonth.split("-");
        LocalDate now = LocalDate.now();

        String year = parts[0];
        String month = parts[1];

        if (!yearMonth.matches("\\d{4}-\\d{2}")) {
            throw InvalidLogDateException.EXCEPTION;
        }

        if(Integer.parseInt(month) > 12 || Integer.parseInt(month) < 1 || Integer.parseInt(year) > now.getYear()) {
            throw InvalidLogDateException.EXCEPTION;
        }

        return year + "년" + month + "월";
    }
}
