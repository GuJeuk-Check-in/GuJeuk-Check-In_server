package com.example.gujeuck_server.domain.purpose.presentation;

import com.example.gujeuck_server.domain.purpose.presentation.dto.request.PurposeRequest;
import com.example.gujeuck_server.domain.purpose.presentation.dto.response.PurposeResponse;
import com.example.gujeuck_server.domain.purpose.presentation.dto.request.PurposeMoveRequest;
import com.example.gujeuck_server.domain.purpose.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purpose")
public class PurposeController {
    private final CreatePurposeService createPurposeService;
    private final UpdatePurposeService updatePurposeService;
    private final DeletePurposeService deletePurposeService;
    private final QueryPurposeDetailService queryPurposeDetailService;
    private final QueryPurposeListService queryPurposeListService;
    private final MovePurposeService movePurposeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPurpose(@RequestBody @Valid PurposeRequest request) {
        createPurposeService.execute(request);
    }

    @PatchMapping("/{id}")
    public void updatePurpose(@PathVariable Long id, @RequestBody @Valid PurposeRequest purposeRequest) {
        updatePurposeService.execute(id, purposeRequest);
    }

    @DeleteMapping("/{id}")
    public void deletePurpose(@PathVariable Long id) {
        deletePurposeService.execute(id);
    }

    @GetMapping("/{id}")
    public PurposeResponse getPurpose(@PathVariable Long id) {
        return queryPurposeDetailService.execute(id);
    }

    @GetMapping("/all")
    public List<PurposeResponse> getAllPurpose() {
        return queryPurposeListService.execute();
    }

    @PatchMapping("/move")
    public void movePurpose(@RequestBody PurposeMoveRequest PurposeMoveRequest) {
        movePurposeService.execute(PurposeMoveRequest);
    }
}
