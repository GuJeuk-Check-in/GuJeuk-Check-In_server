package com.example.gujeuck_server.domain.log.presentation.dto.response;

public record VisitStatisticsCount(
        long youthMale,
        long youthFemale,
        long otherMale,
        long otherFemale
) {
}
