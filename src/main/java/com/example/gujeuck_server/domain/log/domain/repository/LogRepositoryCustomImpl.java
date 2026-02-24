package com.example.gujeuck_server.domain.log.domain.repository;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.QLog;
import com.example.gujeuck_server.domain.log.presentation.dto.response.LogExcelResponse;
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
    public List<LogExcelResponse> findAllByCurrentMonth() {

        return jpaQueryFactory
                .select(Projections.constructor(
                        LogExcelResponse.class,
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

    @Override
    public long countByYearMonth(String yearMonth) {

        return jpaQueryFactory
                .select(qLog.count())
                .from(qLog)
                .where(
                        qLog.visitDate.startsWith(yearMonth)
                )
                .fetchOne();
    }

    @Override
    public List<Log> findAllByOrganIdAndVisitDateStartingWithOrderByDateTime(Long organId, String visitDate) {
        return jpaQueryFactory
                .selectFrom(qLog)
                .where(
                        qLog.organ.id.eq(organId),
                        qLog.visitDate.startsWith(visitDate)
                )
                .orderBy(qLog.visitDate.asc(), qLog.visitTime.asc())
                .fetch();
    }
}
