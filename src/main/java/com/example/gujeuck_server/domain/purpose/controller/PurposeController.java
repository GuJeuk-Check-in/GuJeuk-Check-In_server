package com.example.gujeuck_server.domain.purpose.controller;

import com.example.gujeuck_server.domain.purpose.dto.request.PurposeRequest;
import com.example.gujeuck_server.domain.purpose.dto.response.PurposeResponse;
import com.example.gujeuck_server.domain.purpose.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/purpose")
public class PurposeController {
<<<<<<< HEAD
    private final ReadAllPurposeService readAllPurpose;
    private final ReadOnePurposeService readOnePurpose;
    private final CreatePurposeService createPurpose;
    private final UpdatePurposeService updatePurpose;
    private final DeletePurposeService deletePurpose;
=======
    private final CreatePurposeService createPurpose;
    private final UpdatePurposeService updatePurpose;
    private final DeletePurposeService deletePurpose;
    private final ReadAllPurposeService readAllPurpose;
    private final ReadOnePurposeService readOnePurpose;
>>>>>>> origin/feat/admin-CreateUseList

    @PostMapping("/create")
    public void createPurpose(@RequestBody @Valid PurposeRequest purposeRequest) {
        createPurpose.createPurpose(purposeRequest);
    }

<<<<<<< HEAD
    @GetMapping("/all")
    public List<PurposeResponse> readAllPurpose() {
        return readAllPurpose.readAll();
    }

    @GetMapping("/{id}")
    public PurposeResponse readPurposeById(@PathVariable Long id) {
        return readOnePurpose.readById(id);
    }

=======
>>>>>>> origin/feat/admin-CreateUseList
    @PatchMapping("/{id}")
    public void updatePurpose(@PathVariable Long id, @RequestBody @Valid PurposeRequest purposeRequest) {
        updatePurpose.updatePurpose(id, purposeRequest);
    }

    @DeleteMapping("/{id}")
    public void deletePurpose(@PathVariable Long id) {
        deletePurpose.deletePurpose(id);
    }
<<<<<<< HEAD
}
=======

    @GetMapping("/all")
    public List<PurposeResponse> getAllPurpose() {
        return readAllPurpose.readAll();
    }

    @GetMapping("/{id}")
    public PurposeResponse getOnePurpose(@PathVariable Long id) {
        return readOnePurpose.readById(id);
    }
}

>>>>>>> origin/feat/admin-CreateUseList
