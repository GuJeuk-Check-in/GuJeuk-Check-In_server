package com.example.gujeuck_server.domain.log.presentation.dto.response;

import org.springframework.data.domain.Slice;

public class SliceWithTotalResponse<T> {
    private long totalCount;
    private Slice<T> slice;
}
