package com.example.burserker.gujeuk.domain.organ.presentation.dto.response.organ;

public record VisitStatisticsCount(
        long youthMale,
        long youthFemale,
        long otherMale,
        long otherFemale
) {
}
