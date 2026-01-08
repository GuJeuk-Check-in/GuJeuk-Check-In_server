package com.example.gujeuck_server.domain.user.presentation.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class UserResponse {
    private long totalCount;
    private List<UserDto> users;

    public UserResponse(long totalCount, List<UserDto> users) {
        this.totalCount = totalCount;
        this.users = users;
    }
}