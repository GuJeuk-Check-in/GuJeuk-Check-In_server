package com.example.gujeuck_server.domain.pet.service;

import com.example.gujeuck_server.domain.pet.domain.enums.PetItemCategory;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.ShopItemResponse;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.ShopItemsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryShopItemsService {
    public ShopItemsResponse execute() {
        return new ShopItemsResponse(
                toResponses(PetItemCategory.HAT),
                toResponses(PetItemCategory.SHIRT),
                toResponses(PetItemCategory.PROP),
                toResponses(PetItemCategory.BUFF)
        );
    }

    private List<ShopItemResponse> toResponses(PetItemCategory category) {
        return PetShopCatalog.itemsByCategory(category).stream()
                .map(item -> new ShopItemResponse(item.getId(), item.getName(), item.getCost(), item.getEffect()))
                .toList();
    }
}
