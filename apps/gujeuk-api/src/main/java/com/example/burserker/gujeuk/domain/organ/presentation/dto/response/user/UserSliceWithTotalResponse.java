package com.example.burserker.gujeuk.domain.organ.presentation.dto.response.user;

import org.springframework.data.domain.Slice;

public record UserSliceWithTotalResponse(
        long totalCount,
        Slice<UserInfoResponse> slice
) {

}