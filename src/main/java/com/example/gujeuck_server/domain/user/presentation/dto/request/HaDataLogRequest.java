package com.example.gujeuck_server.domain.user.presentation.dto.request;

public record HaDataLogRequest(
    Long id,
    String purpose,
    int maleCount,
    int femaleCount
) {
}
