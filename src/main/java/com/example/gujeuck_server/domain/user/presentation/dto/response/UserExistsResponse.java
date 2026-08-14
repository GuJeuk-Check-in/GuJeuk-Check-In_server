package com.example.gujeuck_server.domain.user.presentation.dto.response;

import java.util.List;

public record UserExistsResponse(
        boolean userExists,
        List<Long> userIds
) {
    public static UserExistsResponse of(boolean userExists, List<Long> userIds) {
        return new UserExistsResponse(userExists, userIds);
    }
}
