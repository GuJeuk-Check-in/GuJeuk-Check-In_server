package com.example.gujeuck_server.domain.user.presentation.dto.request;

import java.time.LocalDateTime;

public record HaDataLogRequest(
    String clientRecordId,
    Long id,
    String purpose,
    int maleCount,
    int femaleCount,
    LocalDateTime visitTime
) {
}
