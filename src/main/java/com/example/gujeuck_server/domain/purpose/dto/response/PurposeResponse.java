package com.example.gujeuck_server.domain.purpose.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurposeResponse {
    private Long id;
    private String purpose;
    private String purposeImage;
}
