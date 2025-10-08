package com.example.gujeuck_server.domain.admin.dto;

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
}
