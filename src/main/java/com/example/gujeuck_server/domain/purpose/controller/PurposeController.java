package com.example.gujeuck_server.domain.purpose.controller;

import com.example.gujeuck_server.domain.purpose.dto.request.PurposeRequest;
import com.example.gujeuck_server.domain.purpose.dto.response.PurposeResponse;
import com.example.gujeuck_server.domain.purpose.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purpose")
public class PurposeController {
    private final ReadAllPurposeService readAllPurpose;
    private final ReadOnePurposeService readOnePurpose;
    private final CreatePurposeService createPurpose;
    private final UpdatePurposeService updatePurpose;
    private final DeletePurposeService deletePurpose;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createPurpose(@RequestPart(name = "request") PurposeRequest request,
                              @RequestPart(name = "image") MultipartFile image) {
        createPurpose.createPurpose(request, image);
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
