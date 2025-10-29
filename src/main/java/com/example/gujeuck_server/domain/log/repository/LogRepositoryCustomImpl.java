package com.example.gujeuck_server.domain.log.repository;

import com.example.gujeuck_server.domain.log.dto.response.LogResponse;
import com.example.gujeuck_server.domain.log.entity.Log;
import com.example.gujeuck_server.domain.log.entity.QLog;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class LogRepositoryCustomImpl implements LogRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QLog qLog = QLog.log;

    LocalDate now = LocalDate.now();
    int currentYear = now.getYear();
    String currentYearMonth = String.format("%d년%02d월", currentYear, now.getMonthValue());

    @Override
    public List<LogResponse> findAllByCurrentMonth() {

        return jpaQueryFactory
                .select(Projections.constructor(
                        LogResponse.class,
                        qLog.visitDate,
                        qLog.visitTime,
                        qLog.name,
                        qLog.age,
                        qLog.maleCount,
                        qLog.femaleCount,
                        qLog.phone,
                        qLog.purpose,
                        qLog.residence,
                        qLog.privacyAgreed
                ))
                .from(qLog)
                .where(
                        qLog.year.eq(currentYear),
                        qLog.visitDate.startsWith(currentYearMonth)
                )
                .orderBy(qLog.visitDate.asc())
                .fetch();
    }

    @Override
    public Optional<Log> findByUserIdAndVisitTime(String userId, String visitDate, String visitTime) {

        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(qLog)
                        .where(
                                qLog.user.userId.eq(userId),
                                qLog.visitDate.eq(visitDate),
                                qLog.visitTime.eq(visitTime)
                        )
                        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                        .fetchOne()
        );

    }

}
