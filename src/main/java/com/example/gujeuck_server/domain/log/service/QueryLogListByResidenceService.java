package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.presentation.dto.request.QueryLogListByResidenceRequest;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryLogListResponse;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryLogByResidenceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryLogListByResidenceService {
    private final LogRepository logRepository;
    private final AdminFacade adminFacade;

    public QueryLogByResidenceResponse queryLogListByResidence(
            QueryLogListByResidenceRequest queryLogListByResidenceRequest,
            Pageable p
    ) {
        adminFacade.currentUser();

        Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        Slice<QueryLogListResponse> slice = logRepository.findByYearAndMonth(
                queryLogListByResidenceRequest.getYear(),
                queryLogListByResidenceRequest.getMonth(),
                pageable
        );

        long total = logRepository.countByYearAndMonth(
                queryLogListByResidenceRequest.getYear(),
                queryLogListByResidenceRequest.getMonth()
        );

        return new QueryLogByResidenceResponse(
                total,
                slice.getContent()
        );
    }
}
