package com.example.gujeuck_server.domain.log.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class QueryLogByResidenceResponse {

    private long totalCount;
    private List<QueryLogListResponse> content;
}
