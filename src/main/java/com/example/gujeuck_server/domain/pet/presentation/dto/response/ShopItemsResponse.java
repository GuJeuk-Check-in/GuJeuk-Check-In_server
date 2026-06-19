package com.example.gujeuck_server.domain.pet.presentation.dto.response;

import java.util.List;

public record ShopItemsResponse(
        List<ShopItemResponse> hat,
        List<ShopItemResponse> shirt,
        List<ShopItemResponse> prop,
        List<ShopItemResponse> buff
) {
}
