package com.example.gujeuck_server.domain.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UseListRequest {

    @NotBlank(message = "이름은 빈칸으로 둘 수 없습니다.")
    private String name;

    @NotBlank(message = "나이를 선택해주세요")
    private String age;

    @NotBlank(message = "전화번호를 입력해주세요")
    private String phone;

    @NotBlank(message = "남, 여 인원수는 1명 이상 선택해야합니다.")
    private int maleCount;

    @NotBlank(message = "남, 여 인원수는 1명 이상 선택해야합니다.")
    private int femaleCount;

    @NotBlank(message = "발문목적을 선택해주세요.")
    private Long purposeId;

    @NotBlank(message = "방문날짜를 선택해주세요.")
    private LocalDate visitDate;
}