package com.example.gujeuck_server.domain.organ.presentation.dto.response.user;

import org.springframework.data.domain.Slice;

public record UserSliceWithTotalResponse(

        long totalCount,
        Slice<UserInfoResponse> slice
) {

}