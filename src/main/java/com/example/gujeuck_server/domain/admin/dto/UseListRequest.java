package com.example.gujeuck_server.domain.admin.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UseListRequest {

    @Max(value = 30, message = "이름을 30자 이상으로 설정할 수 없습니다.")
    @NotBlank(message = "이름은 빈칸으로 둘 수 없습니다.")
    private String name;

    @NotBlank(message = "나이를 선택해주세요")
    private String age;

    @Max(value = 30, message = "전화번호를 11자리 이상으로 설정할 수 없습니다.")
    @NotBlank(message = "전화번호를 입력해주세요")
    private String phone;

    @NotNull(message = "남, 여 인원수는 1명 이상 선택해야합니다.")
    private int maleCount;

    @NotNull(message = "남, 여 인원수는 1명 이상 선택해야합니다.")
    private int femaleCount;

    @NotNull(message = "발문목적을 선택해주세요.")
    private Long purposeId;

    @NotNull(message = "방문날짜를 선택해주세요.")
    private LocalDate visitDate;

    @AssertTrue(message = "개인정보수집이용및동를 체크해주세요")
    @NotNull(message = "개인정보수집이용및동의를 선택해주세요")
    private boolean privacyAgreed;
}