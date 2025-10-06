package com.example.gujeuck_server.domain.Purpose.controller;

import com.example.gujeuck_server.domain.purpose.dto.PurposeRequest;
import com.example.gujeuck_server.domain.purpose.dto.PurposeResponse;
import com.example.gujeuck_server.domain.purpose.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/purpose")
public class PurposeController {
    private final ReadAllPurposeService readAllPurpose;
    private final ReadOnePurposeService readOnePurpose;
    private final CreatePurposeService createPurpose;
    private final UpdatePurposeService updatePurpose;
    private final DeletePurposeService deletePurpose;

    @PostMapping("/create")
    public void createPurpose(@RequestBody @Valid PurposeRequest purposeRequest) {
        createPurpose.createPurpose(purposeRequest);
    }

    @GetMapping("/all")
    public List<PurposeResponse> readAllPurpose() {
        return readAllPurpose.readAll();
    }

    @GetMapping("/{id}")
    public PurposeResponse readPurposeById(@PathVariable Long id) {
        return readOnePurpose.readById(id);
    }

    @PatchMapping("/{id}")
    public void updatePurpose(@PathVariable Long id, @RequestBody @Valid PurposeRequest purposeRequest) {
        updatePurpose.updatePurpose(id, purposeRequest);
    }

    @DeleteMapping("/{id}")
    public void deletePurpose(@PathVariable Long id) {
        deletePurpose.deletePurpose(id);
    }
}
