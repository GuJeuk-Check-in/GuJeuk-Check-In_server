package com.example.burserker.gujeuk.global.utility;

import com.example.burserker.gujeuk.domain.user.domain.enums.Age;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CalculateAgeService {

    public Age getAge(String birthYMD) {

        LocalDate birthDate = LocalDate.parse(birthYMD);

        int koreanAge = LocalDate.now().getYear() - birthDate.getYear() + 1;

        return Age.from(koreanAge);
    }

}
