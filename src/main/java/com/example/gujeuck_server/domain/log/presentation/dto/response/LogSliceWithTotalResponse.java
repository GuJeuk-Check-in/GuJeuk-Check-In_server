package com.example.gujeuck_server.domain.log.presentation.dto.response;

import com.example.gujeuck_server.domain.log.domain.Log;
import lombok.Builder;
import org.springframework.data.domain.Slice;

@Builder
public record LogSliceWithTotalResponse(
        long totalCount,
        Slice<QueryLogListResponse> slice
) {
    public static LogSliceWithTotalResponse of(long totalCount, Slice<QueryLogListResponse> slice) {
        return LogSliceWithTotalResponse.builder()
            .totalCount(totalCount)
            .slice(slice)
            .build();
    }
}
