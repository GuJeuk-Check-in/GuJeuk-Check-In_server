package com.example.gujeuck_server.domain.admin.controller;

import com.example.gujeuck_server.domain.admin.dto.UseListRequest;
import com.example.gujeuck_server.domain.admin.service.*;
import com.example.gujeuck_server.domain.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final CreateUseListService createUseListService;
    private final ReadOneUserListService readOneUserListService;
    private final ReadAllUserListService readAllUserListService;
    private final DeleteUseListService deleteUseListService;
    private final UpdateUseListService updateUseListService;

    @PostMapping("/list/create")
    public void createUseList(@RequestBody @Valid UseListRequest useListRequest) {
        createUseListService.creatUseList(useListRequest);
    }

    @GetMapping("/user/{id}")
    public UserResponse getOneUser(@PathVariable Long id) {
        return readOneUserListService.readOneUserList(id);
    }

    @GetMapping("/user/all")
    public List<UserResponse> getAllUserList() {
        return readAllUserListService.readAllUserList();
    }

    @DeleteMapping("/list/{id}")
    public void deleteUseList(@PathVariable Long id) {
        deleteUseListService.deleteUseList(id);
    }

    @PatchMapping("/list/{id}")
    public void updateUseList(@PathVariable Long id, @RequestBody @Valid UseListRequest useListRequest) {
        updateUseListService.updateLog(id, useListRequest);
    }
}
