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
    private final CreatePurposeService createPurpose;
    private final UpdatePurposeService updatePurpose;
    private final DeletePurposeService deletePurpose;
<<<<<<< HEAD
<<<<<<< HEAD
    private final ReadAllPurposeService readAllPurpose;
    private final ReadOnePurposeService readOnePurpose;
=======
    private final ReadOnePurposeService readOnePurpose;
    private final ReadAllPurposeService readAllPurpose;
>>>>>>> origin/feat/admin-UpdateUseList
=======
    private final ReadOnePurposeService readOnePurpose;
    private final ReadAllPurposeService readAllPurpose;
>>>>>>> origin/feat/admin-UpdateUseList

    @PostMapping("/create")
    public void createPurpose(@RequestBody @Valid PurposeRequest purposeRequest) {
        createPurpose.createPurpose(purposeRequest);
    }

    @PatchMapping("/{id}")
    public void updatePurpose(@PathVariable Long id, @RequestBody @Valid PurposeRequest purposeRequest) {
        updatePurpose.updatePurpose(id, purposeRequest);
    }

    @DeleteMapping("/{id}")
    public void deletePurpose(@PathVariable Long id) {
        deletePurpose.deletePurpose(id);
    }

<<<<<<< HEAD
<<<<<<< HEAD
    @GetMapping("/{id}")
    public PurposeResponse getPurpose(@PathVariable Long id) {
        return readOnePurpose.readById(id);
    }

=======
>>>>>>> origin/feat/admin-UpdateUseList
=======
>>>>>>> origin/feat/admin-UpdateUseList
    @GetMapping("/all")
    public List<PurposeResponse> getAllPurpose() {
        return readAllPurpose.readAll();
    }
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> origin/feat/admin-UpdateUseList

    @GetMapping("/{id}")
    public PurposeResponse getPurposeById(@PathVariable Long id) {
        return readOnePurpose.readById(id);
    }
<<<<<<< HEAD
>>>>>>> origin/feat/admin-UpdateUseList
=======
>>>>>>> origin/feat/admin-UpdateUseList
}
