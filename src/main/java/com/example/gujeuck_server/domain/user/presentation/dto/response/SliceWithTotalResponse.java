package com.example.gujeuck_server.domain.user.presentation.dto.response;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Slice;

@AllArgsConstructor
public class SliceWithTotalResponse<T> {

    private long totalCount;

    private Slice<T> slice;
}
