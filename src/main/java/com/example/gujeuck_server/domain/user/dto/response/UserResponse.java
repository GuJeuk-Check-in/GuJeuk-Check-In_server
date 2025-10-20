package com.example.gujeuck_server.domain.user.dto.response;

import com.example.gujeuck_server.domain.user.entity.User;
import com.example.gujeuck_server.domain.user.entity.enums.Age;
import com.example.gujeuck_server.domain.user.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private Age age;
    private Gender gender;
    private String phone;
    private String birthYMD;
    private String residence;
    private boolean privacyAgreed;

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getGender(),
                user.getPhone(),
                user.getBirthYMD(),
                user.getResidence(),
                user.isPrivacyAgreed()
        );
    }
}
