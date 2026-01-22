package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryLogListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryLogListService {
    private final LogRepository logRepository;
    private final OrganFacade organFacade;

    @Transactional(readOnly = true)
    public Slice<QueryLogListResponse> execute(Pageable p) {
        Organ organ = organFacade.currentUser();

         Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.DESC, "visitDate")
                        .and(Sort.by(Sort.Direction.DESC, "id"))
                );

        return logRepository.findAllByOrganId(pageable, organ.getId())
                .map(QueryLogListResponse::from);
    }
}
