package com.example.gujeuck_server.domain.user.presentation.dto.response;

public record UserExistsResponse(
        boolean userExists,
        Long userId
) {
    public static UserExistsResponse of(boolean userExists, Long userId) {
        return new UserExistsResponse(userExists, userId);
    }
}
