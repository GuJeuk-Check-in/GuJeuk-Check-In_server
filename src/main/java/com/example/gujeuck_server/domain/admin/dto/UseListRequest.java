package com.example.gujeuck_server.domain.admin.dto;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UseListRequest {
    private String name;
    private String age;
    private String phone;

    @JsonProperty("male_count")
    private int maleCount;

    @JsonProperty("female_count")
    private int femaleCount;

    @JsonProperty("purpose_id")
    private Long purposeId;

    @JsonProperty("visit_date")
    private LocalDate visitDate;
=======
import com.example.gujeuck_server.domain.user.entity.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UseListRequest {
    private String leaderName;
    private String leaderAge;
    private String leaderPhone;
    private int maleCount;
    private int femaleCount;
    private List<CompanionDto> companionList;
    private Gender gender;
    private String purpose;
    private LocalDate visitDate;
    private boolean privacyAgreed;
>>>>>>> origin/feat/admin-CreateUseList
}
