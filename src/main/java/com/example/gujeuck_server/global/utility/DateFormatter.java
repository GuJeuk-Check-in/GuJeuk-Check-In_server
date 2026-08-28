package com.example.gujeuck_server.global.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.example.gujeuck_server.domain.log.exception.InvalidLogDateException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateFormatter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy년MM월dd일");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static String LocalDateForm(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    // 방문 시각(LocalDateTime)에서 저장용 날짜 문자열("yyyy년MM월dd일")을 만든다.
    public static String toVisitDate(LocalDateTime dateTime) {
        return dateTime.toLocalDate().format(DATE_FORMATTER);
    }

    // 방문 시각(LocalDateTime)에서 저장용 시간 문자열("HH:mm")을 만든다.
    public static String toVisitTime(LocalDateTime dateTime) {
        return dateTime.toLocalTime().format(TIME_FORMATTER);
    }

    /**
     * 저장용 날짜·시간 문자열을 LocalDateTime 으로 되돌린다.
     *
     * 관리자가 직접 입력하는 기록은 분 단위까지만 받으므로 초는 0 이 된다.
     * 형식이 어긋나면 InvalidLogDateException 을 던진다.
     */
    public static LocalDateTime toVisitAt(String visitDate, String visitTime) {
        try {
            return LocalDateTime.of(
                    LocalDate.parse(visitDate, DATE_FORMATTER),
                    LocalTime.parse(visitTime, TIME_FORMATTER)
            );
        } catch (DateTimeParseException exception) {
            throw InvalidLogDateException.EXCEPTION;
        }
    }
}
