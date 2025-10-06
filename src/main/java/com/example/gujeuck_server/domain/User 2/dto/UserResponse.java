package com.example.gujeuck_server.domain.User.dto;

import com.example.gujeuck_server.domain.User.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String userId;
    private String name;
    private String gender;
    private String phone;
    private String birthYMD;
    private String residence;
    private boolean privacyAgreed;

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getUserId(),
                user.getName(),
                user.getGender(),
                user.getPhone(),
                user.getBirthYMD(),
                user.getResidence(),
                user.isPrivacyAgreed()
        );
    }
}
