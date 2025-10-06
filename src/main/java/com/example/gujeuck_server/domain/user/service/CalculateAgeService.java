package com.example.gujeuck_server.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CalculateAgeService {

    public static int calculateKoreanAge(String birthYMD) {
        LocalDate birthDate = LocalDate.parse(birthYMD);
        int birthYear = birthDate.getYear();
        int currentYear = LocalDate.now().getYear();
        return currentYear - birthYear + 1;
    }
}
