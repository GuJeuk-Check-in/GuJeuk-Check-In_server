package com.example.burserker.gujeuk.domain.organ.presentation.dto.response;

import com.example.burserker.gujeuk.domain.organ.presentation.dto.response.user.UserListResponse;
import lombok.Builder;
import java.util.List;

@Builder
public record UserWithTotalResponse (
        long totalCount,
        List<UserListResponse> content
) {
    public static UserWithTotalResponse of(long totalCount, List<UserListResponse>content) {
        return UserWithTotalResponse.builder()
                .totalCount(totalCount)
                .content(content)
                .build();
    }

}
