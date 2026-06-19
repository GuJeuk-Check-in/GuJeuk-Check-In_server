package com.example.gujeuck_server.domain.pet.presentation;

import com.example.gujeuck_server.domain.pet.presentation.dto.response.InventoryResponse;
import com.example.gujeuck_server.domain.pet.service.QueryInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InventoryController {
    private final QueryInventoryService queryInventoryService;

    @GetMapping("/inventory")
    public InventoryResponse queryInventory() {
        return queryInventoryService.execute();
    }
}
