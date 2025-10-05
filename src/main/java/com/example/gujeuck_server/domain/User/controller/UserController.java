package com.example.gujeuck_server.domain.User.controller;

import com.example.gujeuck_server.domain.User.dto.UserResponse;
import com.example.gujeuck_server.domain.User.service.ReadAllUserListService;
import com.example.gujeuck_server.domain.User.service.ReadOneUserListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserController {
    private final ReadAllUserListService readAllUserListService;
    private final ReadOneUserListService readOneUserListService;

    @GetMapping("/all")
    public List<UserResponse> getAllUserList() {
        return readAllUserListService.readAllUserList();
    }

    @GetMapping("/{id}")
    public UserResponse getOneUserList(@PathVariable Long id) {
        return readOneUserListService.readOneUserList(id);
    }
}
