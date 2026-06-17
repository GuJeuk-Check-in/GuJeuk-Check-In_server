package com.example.gujeuck_server.domain.pet.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class PurchaseItemRequest {
    @NotBlank(message = "itemId를 입력해주세요.")
    private String itemId;

    private boolean autoEquip;
}
