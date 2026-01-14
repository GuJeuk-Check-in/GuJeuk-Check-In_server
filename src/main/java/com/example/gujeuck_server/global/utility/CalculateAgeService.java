package com.example.gujeuck_server.global.utility;

import com.example.gujeuck_server.domain.user.domain.enums.Age;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CalculateAgeService {

    public Age getAge(String birthYMD) {

        LocalDate birthDate = LocalDate.parse(birthYMD);

        int koreanAge = birthDate.getYear() - LocalDate.now().getYear() + 1;

        return Age.from(koreanAge);
    }

}
