package com.example.gujeuck_server.domain.log.repository;

import com.example.gujeuck_server.domain.log.dto.response.LogResponse;
import com.example.gujeuck_server.domain.log.entity.QLog;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class LogRepositoryCustomImpl implements LogRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QLog qLog = QLog.log;

    LocalDate now = LocalDate.now();
    int currentYear = now.getYear();
    String currentMonth = String.format("%02d월", now.getMonthValue()); // "10월"

    @Override
    public List<LogResponse> findAllByCurrentMonth() {

        return jpaQueryFactory
                .select(Projections.constructor(
                        LogResponse.class,
                        qLog.visitDate,
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
                        qLog.visitDate.startsWith(currentMonth)
                )
                .orderBy(qLog.visitDate.asc())
                .fetch();
    }

}
