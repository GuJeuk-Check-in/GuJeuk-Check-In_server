package com.example.gujeuck_server.domain.log.presentation.dto.request;

import com.example.gujeuck_server.domain.user.domain.enums.Age;
import jakarta.validation.constraints.*;

public record LogRequest (
    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Size(max = 30, message = "이름은 30자 이하로 입력해주세요.")
    String name,

    @NotNull(message = "나이를 선택해주세요.")
    Age age,

    @NotBlank(message = "전화번호를 입력해주세요.")
    String phone,

    @NotNull(message = "남자 동행인 수는 null일 수 없습니다.")
    int maleCount,

    @NotNull(message = "여자 동행인 수는 null일 수 없습니다.")
    int femaleCount,

    @NotBlank(message = "방문 목적을 선택해주세요.")
    String purpose,

    @AssertTrue(message = "개인정보 수집 및 이용 동의를 체크해주세요.")
    boolean privacyAgreed,

    @NotBlank(message = "방문일자를 비워둘 수 없습니다.")
    String visitDate,

    @NotBlank(message = "방문시각을 비워둘 수 없습니다.")
    String visitTime
) {
}
