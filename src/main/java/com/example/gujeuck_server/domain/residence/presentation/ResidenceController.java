package com.example.gujeuck_server.domain.residence.presentation;

import com.example.gujeuck_server.domain.residence.presentation.dto.request.ResidenceMoveRequest;
import com.example.gujeuck_server.domain.residence.presentation.dto.request.ResidenceRequest;
import com.example.gujeuck_server.domain.residence.presentation.dto.response.ResidenceResponse;
import com.example.gujeuck_server.domain.residence.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/residence")
public class ResidenceController {
    private final CreateResidenceService createResidenceService;
    private final DeleteResidenceService deleteResidenceService;
    private final UpdateResidenceService updateResidenceService;
    private final QueryResidenceDetailService queryResidenceDetailService;
    private final QueryResidenceListService queryResidenceListService;
    private final MoveResidenceService moveResidenceService;

    @PostMapping
    public void createResidence(@RequestBody ResidenceRequest residenceRequest){
        createResidenceService.execute(residenceRequest);
    }

    @GetMapping("/all")
    public List<ResidenceResponse> execute(){
        return queryResidenceListService.execute();
    }

    @GetMapping("/{id}")
    public ResidenceResponse execute(@PathVariable Long id){
        return queryResidenceDetailService.execute(id);
    }

    @PatchMapping("/{id}")
    public void updateResidence(@PathVariable Long id, @RequestBody ResidenceRequest residenceRequest){
        updateResidenceService.execute(id, residenceRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteResidence(@PathVariable Long id){
        deleteResidenceService.execute(id);
    }

    @PatchMapping("/move")
    public void moveResidence(@RequestBody ResidenceMoveRequest residenceMoveRequest) {
        moveResidenceService.execute(residenceMoveRequest);
    }
}
