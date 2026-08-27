package com.example.gujeuck_server.domain.log.presentation.dto.request;

import com.example.gujeuck_server.domain.user.domain.enums.Age;
import jakarta.validation.constraints.*;

public record LogRequest (

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Size(max = 10, message = "이름은 10자 이하로 입력해주세요.")
    String name,

    @NotNull(message = "나이를 선택해주세요.")
    @Size(max = 6, message = "나이 입력값이 유효하지 않습니다.")
    Age age,

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Size(max = 15, message = "전화번호를 15자 미만으로 입력해주세요.")
    String phone,

    @NotNull(message = "남자 동행인 수는 null일 수 없습니다.")
    int maleCount,

    @NotNull(message = "여자 동행인 수는 null일 수 없습니다.")
    int femaleCount,

    @NotBlank(message = "방문 목적을 선택해주세요.")
    @Size(max = 30, message = "방문 목적이 유효하지 않습니다.")
    String purpose,

    @AssertTrue(message = "개인정보 수집 및 이용 동의를 체크해주세요.")
    boolean privacyAgreed,

    @NotBlank(message = "방문일자를 비워둘 수 없습니다.")
    @Size(max = 11, message = "날짜 형식이 유효하지 않습니다.")
    String visitDate,

    @NotBlank(message = "방문시각을 비워둘 수 없습니다.")
    @Size(max = 5, message = "방문 시각이 유효하지 않습니다.")
    String visitTime
) {
}