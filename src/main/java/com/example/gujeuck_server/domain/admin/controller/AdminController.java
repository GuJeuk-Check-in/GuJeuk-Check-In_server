package com.example.gujeuck_server.domain.admin.controller;

import com.example.gujeuck_server.domain.admin.dto.UseListRequest;
import com.example.gujeuck_server.domain.admin.service.CreateUseListService;
import com.example.gujeuck_server.domain.admin.service.ReadAllUserListService;
import com.example.gujeuck_server.domain.admin.service.ReadOneUserListService;
import com.example.gujeuck_server.domain.user.dto.UserResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final CreateUseListService createUseListService;
    private final ReadAllUserListService readAllUserListService;
    private final ReadOneUserListService readOneUserListService;

    @PostMapping("/create")
    public void createUseList(@RequestBody UseListRequest useListRequest) {
        createUseListService.creatUseList(useListRequest);
    }

    @GetMapping("/user/all")
    public List<UserResponse> readAllUserList() {
        return readAllUserListService.readAllUserList();
    }

    @GetMapping("/user/{id}")
    public UserResponse readUser(@PathVariable Long id) {
        return readOneUserListService.readOneUserList(id);
    }
}
