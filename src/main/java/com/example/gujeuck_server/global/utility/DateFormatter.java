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

    public static String toVisitDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static String toVisitDate(LocalDateTime dateTime) {
        return toVisitDate(dateTime.toLocalDate());
    }

    public static String toVisitTime(LocalDateTime dateTime) {
        return dateTime.toLocalTime().format(TIME_FORMATTER);
    }

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
