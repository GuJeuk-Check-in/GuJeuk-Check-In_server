package com.example.gujeuck_server.domain.log.domain;

public record VisitStatisticsCount(
        long youthMale,
        long youthFemale,
        long otherMale,
        long otherFemale
) {
}
