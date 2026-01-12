package com.example.gujeuck_server.domain.user.domain;

import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.domain.user.domain.enums.Gender;
import com.example.gujeuck_server.domain.user.domain.enums.Residence;
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

    @Column(nullable = false, unique = true, length = 30)
    private String userId;

    private long allUserCount;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 20, unique = true)
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false, length = 20) //2008-05-04 이런 형태로 받아야 함
    private String birthYMD;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Age age;

    @Column(nullable = false, length = 30)
    private String residence;

    @Column(nullable = false)
    private boolean privacyAgreed;

    @Column(nullable = false)
    private int count;

    public void updateUser(String name, String userId, String phone, Gender gender, String birthYMD, String residence) {}

    public static String generateUserId(String name, String birthYMD) {
        String monthDay = birthYMD.substring(5).replace("-", "");
        return name + monthDay;
    }

    public static String resolveResidence(String input) {

        Residence matched = Residence.fromKoreanName(input.trim());

        return matched != null ? matched.getKoreanName() : input.trim();
    }

    public void increaseCount() {
        this.count = count + 1;
    }
}
