package com.example.gujeuck_server.domain.residence.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResidenceMoveRequest {
    private List<Long> residenceId;
}
