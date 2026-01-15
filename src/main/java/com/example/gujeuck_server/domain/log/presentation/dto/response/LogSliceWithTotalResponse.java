package com.example.gujeuck_server.domain.log.presentation.dto.response;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Slice;

@AllArgsConstructor
public class LogSliceWithTotalResponse<T> {
    private long totalCount;

    private Slice<T> slice;
}
