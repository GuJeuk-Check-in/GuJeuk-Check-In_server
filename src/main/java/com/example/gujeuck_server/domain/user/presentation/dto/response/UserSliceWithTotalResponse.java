package com.example.gujeuck_server.domain.user.presentation.dto.response;

import org.springframework.data.domain.Slice;

public record UserSliceWithTotalResponse(

        long totalCount,
        Slice<UserInfoResponse> slice
) {

}