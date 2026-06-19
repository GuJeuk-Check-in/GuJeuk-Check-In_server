package com.example.gujeuck_server.domain.pet.presentation;

import com.example.gujeuck_server.domain.pet.presentation.dto.request.CreatePetRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.request.PetActionRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.request.SyncPetRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.request.UpdateEquipmentRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.request.UpdatePlacedPropsRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PetActionResponse;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PetResponse;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.SyncPetResponse;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.UpdateEquipmentResponse;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.UpdatePlacedPropsResponse;
import com.example.gujeuck_server.domain.pet.service.CreatePetService;
import com.example.gujeuck_server.domain.pet.service.DoPetActionService;
import com.example.gujeuck_server.domain.pet.service.QueryMyPetService;
import com.example.gujeuck_server.domain.pet.service.SyncPetSnapshotService;
import com.example.gujeuck_server.domain.pet.service.UpdateEquipmentService;
import com.example.gujeuck_server.domain.pet.service.UpdatePlacedPropsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {
    private final CreatePetService createPetService;
    private final QueryMyPetService queryMyPetService;
    private final SyncPetSnapshotService syncPetSnapshotService;
    private final DoPetActionService doPetActionService;
    private final UpdateEquipmentService updateEquipmentService;
    private final UpdatePlacedPropsService updatePlacedPropsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetResponse createPet(@RequestBody @Valid CreatePetRequest request) {
        return createPetService.execute(request);
    }

    @GetMapping("/me")
    public PetResponse queryMyPet() {
        return queryMyPetService.execute();
    }

    @PostMapping("/me/sync")
    public SyncPetResponse syncPet(@RequestBody @Valid SyncPetRequest request) {
        return syncPetSnapshotService.execute(request);
    }

    @PostMapping("/me/actions")
    public PetActionResponse doAction(@RequestBody @Valid PetActionRequest request) {
        return doPetActionService.execute(request);
    }

    @PutMapping("/me/equipment")
    public UpdateEquipmentResponse updateEquipment(@RequestBody @Valid UpdateEquipmentRequest request) {
        return updateEquipmentService.execute(request);
    }

    @PutMapping("/me/props")
    public UpdatePlacedPropsResponse updatePlacedProps(@RequestBody @Valid UpdatePlacedPropsRequest request) {
        return updatePlacedPropsService.execute(request);
    }
}
