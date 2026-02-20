package com.example.gujeuck_server.domain.log.presentation.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Slice;

@Builder
public record LogSliceWithTotalResponse(
        long totalCount,
        Slice<QueryLogListResponse> slice
) {

}
