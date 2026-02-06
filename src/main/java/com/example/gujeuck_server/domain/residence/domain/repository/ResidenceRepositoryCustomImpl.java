package com.example.gujeuck_server.domain.residence.domain.repository;

import com.example.gujeuck_server.domain.residence.domain.QResidence;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResidenceRepositoryCustomImpl implements ResidenceRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QResidence qResidence = QResidence.residence;

    @Override
    public int findMaxResidenceIndexByOrganId(Long organId) {
        Integer max = jpaQueryFactory
                .select(qResidence.residenceIndex.max())
                .where(qResidence.organ.id.eq(organId))
                .from(qResidence)
                .fetchOne();

        return max == null ? 0 : max;
    }
}

