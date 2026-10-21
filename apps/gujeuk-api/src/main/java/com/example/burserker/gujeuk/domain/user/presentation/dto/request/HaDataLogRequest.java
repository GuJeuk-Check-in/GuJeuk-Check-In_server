package com.example.burserker.gujeuk.domain.user.presentation.dto.request;

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
