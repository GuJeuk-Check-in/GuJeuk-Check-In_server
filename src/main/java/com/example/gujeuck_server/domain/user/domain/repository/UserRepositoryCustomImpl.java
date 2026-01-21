package com.example.gujeuck_server.domain.user.domain.repository;

import com.example.gujeuck_server.domain.user.domain.QUser;
import com.example.gujeuck_server.domain.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<User> findByUserIdAndAdminId(String userId, Long adminId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(QUser.user)
                .where(QUser.user.userId.eq(userId), QUser.user.admin.id.eq(adminId))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetchOne()
        );
    }
}
