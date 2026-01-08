package com.example.gujeuck_server.domain.user.presentation.dto.response;

import com.example.gujeuck_server.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String age;
    private String gender;
    private String phone;
    private String birthYMD;
    private String residence;
    private boolean privacyAgreed;
    private int count;

    public static UserDto from(User u) {
        return UserDto.builder()
                .id(u.getId())
                .name(u.getName())
                .age(u.getAge().getLabel())
                .gender(u.getGender().name())
                .phone(u.getPhone())
                .birthYMD(u.getBirthYMD())
                .residence(u.getResidence())
                .privacyAgreed(u.isPrivacyAgreed())
                .count(u.getCount())
                .build();
    }
}

