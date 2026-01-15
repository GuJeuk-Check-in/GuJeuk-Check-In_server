package com.example.gujeuck_server.domain.purpose.presentation.dto.response;

import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import lombok.Builder;

@Builder
public record PurposeResponse(
        Long id,
        String purpose
) {
    public static PurposeResponse from(Purpose purpose) {
        return PurposeResponse.builder()
                .id(purpose.getId())
                .purpose(purpose.getPurposeName())
                .build();
    }
}
