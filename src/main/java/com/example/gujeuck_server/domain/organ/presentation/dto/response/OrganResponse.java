package com.example.gujeuck_server.domain.organ.presentation.dto.response;

import com.example.gujeuck_server.domain.organ.domain.Organ;

public record OrganResponse(

        String OrganName
) {

    public static OrganResponse from(Organ organ) {
        return new OrganResponse(organ.getOrganName());
    }
}
