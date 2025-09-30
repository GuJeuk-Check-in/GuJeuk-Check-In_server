package com.example.gujeuck_server.domain.controller;

import com.example.gujeuck_server.domain.dto.PurposeDto;
import com.example.gujeuck_server.domain.entity.Purpose;
import com.example.gujeuck_server.domain.service.CreatePurpose;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final CreatePurpose createPurpose;

    @PostMapping("/purpose/create")
    public void createPurpose(PurposeDto purposeDto) {
        createPurpose.createPurpose(purposeDto);
    }
}
