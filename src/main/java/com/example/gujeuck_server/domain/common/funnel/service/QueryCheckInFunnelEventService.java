package com.example.gujeuck_server.domain.common.funnel.service;

import com.example.gujeuck_server.domain.common.funnel.domain.repository.CheckInFunnelEventRepository;
import com.example.gujeuck_server.domain.common.funnel.presentation.dto.response.CheckInFunnelEventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryCheckInFunnelEventService {
    private final CheckInFunnelEventRepository checkInFunnelEventRepository;

    @Transactional(readOnly = true)
    public Slice<CheckInFunnelEventResponse> execute(Long organId, Pageable p) {
        Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
                        .and(Sort.by(Sort.Direction.DESC, "id"))
        );

        return checkInFunnelEventRepository.findAllByOrganId(pageable, organId)
                .map(CheckInFunnelEventResponse::from);
    }
}
