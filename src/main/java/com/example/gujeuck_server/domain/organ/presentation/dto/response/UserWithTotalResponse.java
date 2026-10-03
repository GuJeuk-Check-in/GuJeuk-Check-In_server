package com.example.gujeuck_server.domain.organ.presentation.dto.response;

import com.example.gujeuck_server.domain.organ.presentation.dto.response.user.UserInfoResponse;

import java.util.List;

public record UserWithTotalResponse (
        long totalCount,
        List<UserInfoResponse> content
) {

}
