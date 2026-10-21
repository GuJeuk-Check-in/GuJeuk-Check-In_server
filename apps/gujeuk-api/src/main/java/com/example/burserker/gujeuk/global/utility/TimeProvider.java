package com.example.burserker.gujeuk.global.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeProvider {

    private static final ZoneId KOREA_ZONE = ZoneId.of("Asia/Seoul");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ZonedDateTime nowZoned() {
        return ZonedDateTime.now(KOREA_ZONE);
    }

    public static LocalDateTime toKoreaLocalDateTime(Instant instant) {
        return instant.atZone(KOREA_ZONE).toLocalDateTime();
    }
}
