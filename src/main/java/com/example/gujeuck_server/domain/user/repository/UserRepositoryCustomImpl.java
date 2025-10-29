package com.example.gujeuck_server.domain.user.repository;

import com.example.gujeuck_server.domain.user.entity.QUser;
import com.example.gujeuck_server.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QUser qUser = QUser.user;

    @Override
    public Optional<User> findByUserId(String userId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qUser)
                .where(qUser.userId.eq(userId))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetchOne()
        );
    }
}
