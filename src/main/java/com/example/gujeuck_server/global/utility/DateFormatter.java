package com.example.gujeuck_server.global.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateFormatter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy년MM월dd일");

    public static String LocalDateForm(LocalDate date) {
        return date.format(FORMATTER);
    }

}
