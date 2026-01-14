package com.example.gujeuck_server.domain.user.presentation.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UserListResponse(

        long totalCount,

        List<UserDto>users
) {
}
