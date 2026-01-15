package com.example.gujeuck_server.domain.user.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
@AllArgsConstructor
public class UserSliceWithTotalResponse<T> {
    private long totalCount;

    private Slice<T> slice;
}
