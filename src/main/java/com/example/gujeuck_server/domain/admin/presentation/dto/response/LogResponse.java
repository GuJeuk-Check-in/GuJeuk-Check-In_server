package com.example.gujeuck_server.domain.admin.presentation.dto.response;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogResponse {
    private Long id;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Size(max = 30, message = "이름은 30자 이하로 입력해주세요.")
    private String name;

    @NotBlank(message = "나이를 선택해주세요.")
    private Age age;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Size(max = 30, message = "전화번호은 11자리 이상으로 입력할 수 없습니다.")
    private String phone;

    private int maleCount;

    private int femaleCount;

    @NotNull(message = "방문 목적을 선택해주세요.")
    private String purpose;

    @NotNull(message = "방문 날짜를 선택해주세요.")
    private String visitDate;

    @AssertTrue(message = "개인정보 수집 및 이용 동의를 체크해주세요.")
    private boolean privacyAgreed;

    public static LogResponse from(Log log) {
        return LogResponse.builder()
                .id(log.getId())
                .name(log.getName())
                .age(log.getAge())
                .visitDate(log.getVisitDate())
                .femaleCount(log.getFemaleCount())
                .purpose(log.getPurpose())
                .maleCount(log.getMaleCount())
                .privacyAgreed(log.isPrivacyAgreed())
                .phone(log.getPhone())
                .build();
    }

    public LogResponse(Log log) {
        this.id = log.getId();
        this.name = log.getName();
        this.age = log.getAge();
        this.phone = log.getPhone();
        this.maleCount = log.getMaleCount();
        this.femaleCount = log.getFemaleCount();
        this.purpose = log.getPurpose();
        this.visitDate = log.getVisitDate();
        this.privacyAgreed = log.isPrivacyAgreed();
    }
}
