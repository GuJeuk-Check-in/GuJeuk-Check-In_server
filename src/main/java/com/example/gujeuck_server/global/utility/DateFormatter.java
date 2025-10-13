package com.example.gujeuck_server.global.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM월dd일");

    public static String LocalDateForm(LocalDate date) {
        if (date == null) return null;
        return date.format(FORMATTER);
    }
}
