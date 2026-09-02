package com.example.gujeuck_server.domain.residence.presentation;

import com.example.gujeuck_server.domain.residence.presentation.dto.request.ResidenceMoveRequest;
import com.example.gujeuck_server.domain.residence.presentation.dto.request.ResidenceRequest;
import com.example.gujeuck_server.domain.residence.presentation.dto.response.ResidenceResponse;
import com.example.gujeuck_server.domain.residence.service.*;
import com.example.gujeuck_server.global.security.auth.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/residence")
public class ResidenceController {
    private final CreateResidenceService createResidenceService;
    private final DeleteResidenceService deleteResidenceService;
    private final UpdateResidenceService updateResidenceService;
    private final QueryResidenceListService queryResidenceListService;
    private final MoveResidenceService moveResidenceService;

    @PostMapping
    public void createResidence(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid ResidenceRequest residenceRequest
    ) {
        createResidenceService.execute(userDetails.organ().getId(), residenceRequest);
    }

    @GetMapping("/all")
    public List<ResidenceResponse> execute(){
        return queryResidenceListService.execute();
    }

    @PatchMapping("/{id}")
    public void updateResidence(@PathVariable Long id, @RequestBody @Valid ResidenceRequest residenceRequest){
        updateResidenceService.execute(id, residenceRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteResidence(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id
    ) {
        deleteResidenceService.execute(userDetails.organ().getId(), id);
    }

    @PatchMapping("/move")
    public void moveResidence(@RequestBody @Valid ResidenceMoveRequest residenceMoveRequest) {
        moveResidenceService.execute(residenceMoveRequest);
    }
}
