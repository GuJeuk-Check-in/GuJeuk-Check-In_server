package com.example.gujeuck_server.global.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeProvider {

    private static final ZoneId KOREA_ZONE = ZoneId.of("Asia/Seoul");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static LocalDate nowDate() {
        return ZonedDateTime.now(KOREA_ZONE).toLocalDate();
    }

    public static LocalTime nowTime() {
        return ZonedDateTime.now(KOREA_ZONE).toLocalTime();
    }

    public static int nowYear() {
        return ZonedDateTime.now(KOREA_ZONE).getYear();
    }

    public static String nowDateFormatted() {
        return DateFormatter.LocalDateForm(nowDate());
    }

    public static String nowTimeFormatted() {
        return nowTime().format(TIME_FORMATTER);
    }

    public static ZonedDateTime nowZoned() {
        return ZonedDateTime.now(KOREA_ZONE);
    }
}
