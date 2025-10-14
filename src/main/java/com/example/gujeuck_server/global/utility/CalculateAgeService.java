package com.example.gujeuck_server.global.utility;

import com.example.gujeuck_server.domain.user.entity.enums.Age;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculateAgeService {

    public Age getAge(String birthYMD) {
        LocalDate birthDate = LocalDate.parse(birthYMD);
        int birthYear = birthDate.getYear();
        int currentYear = LocalDate.now().getYear();

        int koreanAge = currentYear - birthYear + 1;

        return Age.from(koreanAge);
    }

}
