package com.example.gujeuck_server.domain.pet.presentation;

import com.example.gujeuck_server.domain.pet.presentation.dto.request.PurchaseItemRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PurchaseItemResponse;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.ShopItemsResponse;
import com.example.gujeuck_server.domain.pet.service.PurchaseShopItemService;
import com.example.gujeuck_server.domain.pet.service.QueryShopItemsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {
    private final QueryShopItemsService queryShopItemsService;
    private final PurchaseShopItemService purchaseShopItemService;

    @GetMapping("/items")
    public ShopItemsResponse queryItems() {
        return queryShopItemsService.execute();
    }

    @PostMapping("/purchase")
    public PurchaseItemResponse purchase(@RequestBody @Valid PurchaseItemRequest request) {
        return purchaseShopItemService.execute(request);
    }
}
