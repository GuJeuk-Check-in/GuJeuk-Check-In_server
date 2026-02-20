package com.example.gujeuck_server.domain.residence.presentation.dto.response;

import com.example.gujeuck_server.domain.residence.domain.Residence;
import lombok.Builder;

@Builder
public record ResidenceResponse(
        Long id,
        String residence
) {
    public static ResidenceResponse from(Residence residence) {
        return ResidenceResponse.builder()
                .id(residence.getId())
                .residence(residence.getResidenceName())
                .build();
    }
}
