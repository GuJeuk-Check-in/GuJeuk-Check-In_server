package com.example.gujeuck_server.domain.controller;

import com.example.gujeuck_server.domain.entity.Purpose;
import com.example.gujeuck_server.domain.service.ReadPurpose;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final ReadPurpose readPurpose;

    @GetMapping("/all")
    public List<Purpose> readPurpose() {
        List<Purpose> purposes = readPurpose.readAll();
        return purposes;
    }

    @GetMapping("/{id}")
    public Purpose readPurpose(@PathVariable Long id) {
        Purpose purpose = readPurpose.readById(id);
        return purpose;
    }
}
