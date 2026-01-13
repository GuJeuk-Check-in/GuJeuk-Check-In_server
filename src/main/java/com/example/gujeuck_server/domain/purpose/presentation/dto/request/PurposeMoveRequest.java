package com.example.gujeuck_server.domain.purpose.presentation.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class PurposeMoveRequest {
    private List<Long> purposeId;
}