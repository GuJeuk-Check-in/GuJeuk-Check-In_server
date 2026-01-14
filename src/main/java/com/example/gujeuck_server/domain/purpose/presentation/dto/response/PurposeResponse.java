package com.example.gujeuck_server.domain.purpose.presentation.dto.response;

import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurposeResponse {
    private Long id;
    private String purpose;

    public static PurposeResponse from(Purpose purpose) {
        return PurposeResponse.builder()
                .id(purpose.getId())
                .purpose(purpose.getPurposeName())
                .build();
    }
}
