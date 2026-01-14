package com.example.gujeuck_server.domain.user.presentation.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class UserResponse {
    private long totalCount;
    private List<UserInfoResponse> users;

    public UserResponse(long totalCount, List<UserInfoResponse> users) {
        this.totalCount = totalCount;
        this.users = users;
    }
}