package com.example.gujeuck_server.domain.common.service.funnel;

import com.example.gujeuck_server.domain.common.domain.funnel.repository.CheckInFunnelEventRepository;
import com.example.gujeuck_server.domain.common.presentation.funnel.dto.response.CheckInFunnelEventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryCheckInFunnelEventService {
    private final CheckInFunnelEventRepository checkInFunnelEventRepository;

    @Transactional(readOnly = true)
    public Slice<CheckInFunnelEventResponse> execute(Pageable pageable) {
        return checkInFunnelEventRepository.findAllByOrderByCreatedAtDescIdDesc(pageable)
                .map(CheckInFunnelEventResponse::new);
    }
}
