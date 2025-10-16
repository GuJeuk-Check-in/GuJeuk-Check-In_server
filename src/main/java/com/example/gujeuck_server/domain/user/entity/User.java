package com.example.gujeuck_server.domain.user.entity;

import com.example.gujeuck_server.domain.user.entity.enums.Age;
import com.example.gujeuck_server.domain.user.entity.enums.Gender;
import com.example.gujeuck_server.domain.user.entity.enums.Residence;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 30, name = "user_id")
    private String userId;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false, length = 20, name = "birth_ymd") //2008-05-04 이런 형태로 받아야 함
    private String birthYMD;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Age age;

    @Column(nullable = false, length = 30)
    private String residence;

    @Column(nullable = false, name = "privacy_agreed")
    private boolean privacyAgreed;

    public static String generateUserId(String name, String birthYMD) {
        String monthDay = birthYMD.substring(5).replace("-", "");
        return name + monthDay;
    }

    public static String resolveResidence(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("거주지는 비어 있을 수 없습니다.");
        }

        // Enum에 한글 이름이 존재하는지 확인
        Residence matched = Residence.fromKoreanName(input.trim());

        // 있으면 Enum의 koreanName 반환 (즉, 정상 등록된 동)
        // 없으면 그대로 사용자 입력 (예: 전주, 경주 등)
        return matched != null ? matched.getKoreanName() : input.trim();
    }
}
