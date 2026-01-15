package com.example.gujeuck_server.domain.purpose.domain.repository;

import com.example.gujeuck_server.domain.purpose.domain.QPurpose;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PurposeRepositoryCustomImpl implements PurposeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QPurpose qPurpose = QPurpose.purpose;

    @Override
    public int findMaxPurposeIndex() {
        Integer max = jpaQueryFactory
                .select(qPurpose.purposeIndex.max())
                .from(qPurpose)
                .fetchOne();

        return max == null ? 0 : max;
    }
}
