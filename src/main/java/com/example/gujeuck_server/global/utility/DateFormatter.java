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

    public static String toVisitDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static String toVisitDate(LocalDateTime dateTime) {
        return toVisitDate(dateTime.toLocalDate());
    }

    public static String toVisitTime(LocalDateTime dateTime) {
        return dateTime.toLocalTime().format(TIME_FORMATTER);
    }
}
