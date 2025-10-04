package com.example.gujeuck_server.domain.User.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String gender;
    private String phone;
    private String birthYMD;
    private String residence;
    private boolean privacyAgreed;
}
