package com.example.gujeuck_server.domain.purpose.controller;

import com.example.gujeuck_server.domain.purpose.dto.request.PurposeRequest;
import com.example.gujeuck_server.domain.purpose.dto.response.PurposeResponse;
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
    private final CreatePurposeService createPurpose;
    private final UpdatePurposeService updatePurpose;
    private final DeletePurposeService deletePurpose;
    private final ReadOnePurposeService readOnePurpose;
    private final ReadAllPurposeService readAllPurpose;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPurpose(@RequestBody @Valid PurposeRequest request) {
        createPurpose.createPurpose(request);
    }

    @PatchMapping("/{id}")
    public void updatePurpose(@PathVariable Long id, @RequestBody @Valid PurposeRequest purposeRequest) {
        updatePurpose.updatePurpose(id, purposeRequest);
    }

    @DeleteMapping("/{id}")
    public void deletePurpose(@PathVariable Long id) {
        deletePurpose.deletePurpose(id);
    }

    @GetMapping("/{id}")
    public PurposeResponse getPurpose(@PathVariable Long id) {
        return readOnePurpose.readById(id);
    }

    @GetMapping("/all")
    public List<PurposeResponse> getAllPurpose() {
        return readAllPurpose.readAll();
    }
}
