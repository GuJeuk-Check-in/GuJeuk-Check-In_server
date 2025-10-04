package com.example.gujeuck_server.domain.controller;

import com.example.gujeuck_server.domain.dto.PurposeRequest;
import com.example.gujeuck_server.domain.dto.PurposeResponse;
import com.example.gujeuck_server.domain.service.CreatePurposeService;
import com.example.gujeuck_server.domain.service.ReadAllPurposeService;
import com.example.gujeuck_server.domain.service.ReadOnePurposeService;
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

    @PostMapping("/create")
    public void createPurpose(@RequestBody PurposeRequest purposeRequest) {
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


}
