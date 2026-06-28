package com.example.gujeuck_server.domain.log.domain.repository;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.QLog;
import com.example.gujeuck_server.domain.log.domain.VisitStatisticsCount;
import com.example.gujeuck_server.domain.log.presentation.dto.response.LogExcelResponse;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
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
    private static final List<Age> YOUTH_AGES = List.of(
            Age.AGE_9_13,
            Age.AGE_14_16,
            Age.AGE_17_19,
            Age.AGE_20_24
    );
    private static final List<Age> OTHER_AGES = List.of(
            Age.BABY,
            Age.ADULT
    );

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
    public Optional<Log> findByUserIdAndVisitTime(Long userId, String visitDate, String visitTime) {

        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(qLog)
                        .where(
                                qLog.user.id.eq(userId),
                                qLog.visitDate.eq(visitDate),
                                qLog.visitTime.eq(visitTime)
                        )
                        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                        .fetchOne()
        );
    }

    @Override
    public long countByYearMonth(Long organId, String yearMonth) {

        return jpaQueryFactory
                .select(qLog.count())
                .from(qLog)
                .where(
                        qLog.organ.id.eq(organId),
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

    @Override
    public VisitStatisticsCount summarizeVisits(Long organId, String startVisitDate, String endVisitDate) {
        NumberExpression<Long> youthMale = new CaseBuilder()
                .when(qLog.age.in(YOUTH_AGES))
                .then(qLog.maleCount.longValue())
                .otherwise(0L)
                .sum();
        NumberExpression<Long> youthFemale = new CaseBuilder()
                .when(qLog.age.in(YOUTH_AGES))
                .then(qLog.femaleCount.longValue())
                .otherwise(0L)
                .sum();
        NumberExpression<Long> otherMale = new CaseBuilder()
                .when(qLog.age.in(OTHER_AGES))
                .then(qLog.maleCount.longValue())
                .otherwise(0L)
                .sum();
        NumberExpression<Long> otherFemale = new CaseBuilder()
                .when(qLog.age.in(OTHER_AGES))
                .then(qLog.femaleCount.longValue())
                .otherwise(0L)
                .sum();

        Tuple result = jpaQueryFactory
                .select(youthMale, youthFemale, otherMale, otherFemale)
                .from(qLog)
                .where(
                        qLog.organ.id.eq(organId),
                        qLog.visitDate.between(startVisitDate, endVisitDate)
                )
                .fetchOne();

        if (result == null) {
            return new VisitStatisticsCount(0, 0, 0, 0);
        }

        return new VisitStatisticsCount(
                valueOrZero(result.get(youthMale)),
                valueOrZero(result.get(youthFemale)),
                valueOrZero(result.get(otherMale)),
                valueOrZero(result.get(otherFemale))
        );
    }

    private long valueOrZero(Long value) {
        return value == null ? 0 : value;
    }
}
