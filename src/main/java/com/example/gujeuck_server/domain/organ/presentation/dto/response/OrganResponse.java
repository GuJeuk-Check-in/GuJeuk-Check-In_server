package com.example.gujeuck_server.domain.organ.presentation.dto.response;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import lombok.Builder;

@Builder
public record OrganResponse(
        String OrganName
) {

    public static OrganResponse from(Organ organ) {
        return OrganResponse.builder()
            .OrganName(organ.getOrganName())
            .build();
    }
}
