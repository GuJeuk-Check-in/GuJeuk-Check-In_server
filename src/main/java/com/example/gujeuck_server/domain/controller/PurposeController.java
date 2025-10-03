package com.example.gujeuck_server.domain.controller;

import com.example.gujeuck_server.domain.dto.PurposeRequest;
import com.example.gujeuck_server.domain.dto.PurposeResponse;
import com.example.gujeuck_server.domain.entity.Purpose;
import com.example.gujeuck_server.domain.service.CreatePurpose;
import com.example.gujeuck_server.domain.service.ReadAllPurpose;
import com.example.gujeuck_server.domain.service.ReadOnePurpose;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/purpose")
public class PurposeController {
    private final ReadAllPurpose readAllPurpose;
    private final ReadOnePurpose readOnePurpose;
    private final CreatePurpose createPurpose;

    @PostMapping("/create")
    public void createPurpose(@RequestBody PurposeRequest purposeRequest) {
        createPurpose.createPurpose(purposeRequest);
    }

    @GetMapping("/all")
    public List<PurposeResponse> readAllPurpose() {
        return readAllPurpose.readAll();
    }

    @GetMapping("/{id}")
    public Purpose readPurposeById(@PathVariable Long id) {
        return readOnePurpose.readById(id);
    }


}
