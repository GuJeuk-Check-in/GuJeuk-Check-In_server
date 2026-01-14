package com.example.gujeuck_server.domain.user.presentation.dto.request;

import com.example.gujeuck_server.domain.user.domain.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignupRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotNull(message = "성별을 입력해주세요.")
    private Gender gender;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phone;

    @NotNull(message = "남자 동행인 수는 null일 수 없습니다.")
    private int maleCount;

    @NotNull(message = "여자 동행인 수는 null일 수 없습니다.")
    private int femaleCount;

    @NotBlank(message = "생년월일을 입력해주세요.")
    private String birthYMD;

    @NotBlank(message = "거주지를 입력해주세요.")
    private String residence;

    @NotNull(message = "개인정보 동의 여부를 입력해주세요.")
    private boolean privacyAgreed;

    @NotBlank(message = "방문목적을 입력해주세요.")
    private String purpose;
}
