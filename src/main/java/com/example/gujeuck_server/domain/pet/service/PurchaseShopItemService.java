package com.example.gujeuck_server.domain.pet.service;

import com.example.gujeuck_server.domain.pet.domain.Pet;
import com.example.gujeuck_server.domain.pet.domain.enums.PetItemCategory;
import com.example.gujeuck_server.domain.pet.exception.AlreadyOwnedException;
import com.example.gujeuck_server.domain.pet.exception.InsufficientPointsException;
import com.example.gujeuck_server.domain.pet.facade.PetFacade;
import com.example.gujeuck_server.domain.pet.facade.PetUserFacade;
import com.example.gujeuck_server.domain.pet.presentation.dto.request.PurchaseItemRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PetEquippedResponse;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PetInventoryResponse;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PurchaseItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseShopItemService {
    private final PetFacade petFacade;
    private final PetUserFacade petUserFacade;

    @Transactional
    public PurchaseItemResponse execute(PurchaseItemRequest request) {
        Pet pet = petFacade.getWithLockByPetUserId(petUserFacade.currentUser().getId());
        PetShopCatalog.PetShopItem item = PetShopCatalog.get(request.getItemId().trim());

        validateNotOwned(pet, item);
        if (pet.getPoints() < item.getCost()) {
            throw InsufficientPointsException.EXCEPTION;
        }

        pet.spendPoints(item.getCost());
        addOwnership(pet, item);

        if (request.isAutoEquip()) {
            autoEquip(pet, item);
        }

        return new PurchaseItemResponse(pet.getPoints(), PetInventoryResponse.from(pet), PetEquippedResponse.from(pet));
    }

    private void validateNotOwned(Pet pet, PetShopCatalog.PetShopItem item) {
        boolean alreadyOwned = switch (item.getCategory()) {
            case HAT -> pet.getOwnedHats().contains(item.getId());
            case SHIRT -> pet.getOwnedShirts().contains(item.getId());
            case PROP -> pet.getOwnedProps().contains(item.getId());
            case BUFF -> pet.getOwnedBuffs().contains(item.getId());
        };

        if (alreadyOwned) {
            throw AlreadyOwnedException.EXCEPTION;
        }
    }

    private void addOwnership(Pet pet, PetShopCatalog.PetShopItem item) {
        switch (item.getCategory()) {
            case HAT -> pet.addOwnedHat(item.getId());
            case SHIRT -> pet.addOwnedShirt(item.getId());
            case PROP -> pet.addOwnedProp(item.getId());
            case BUFF -> pet.addOwnedBuff(item.getId());
        }
    }

    private void autoEquip(Pet pet, PetShopCatalog.PetShopItem item) {
        if (item.getCategory() == PetItemCategory.HAT) {
            pet.equip(item.getId(), pet.getEquippedShirt());
        } else if (item.getCategory() == PetItemCategory.SHIRT) {
            pet.equip(pet.getEquippedHat(), item.getId());
        }
    }
}
