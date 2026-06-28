package com.example.gujeuck_server.global.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
}
