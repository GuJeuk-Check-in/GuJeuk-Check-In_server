package com.example.gujeuck_server.domain.log.presentation.dto.response;

import org.springframework.data.domain.Slice;

public record LogSliceWithTotalResponse(
        long totalCount,
        Slice<QueryLogListResponse> slice
) {

}
