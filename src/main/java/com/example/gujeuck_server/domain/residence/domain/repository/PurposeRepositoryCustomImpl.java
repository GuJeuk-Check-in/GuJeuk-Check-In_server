package com.example.gujeuck_server.domain.residence.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PurposeRepositoryCustomImpl implements ResidenceRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}

