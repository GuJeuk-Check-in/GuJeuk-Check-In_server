package com.example.gujeuck_server.domain.User.controller;

import com.example.gujeuck_server.domain.User.dto.UserResponse;
import com.example.gujeuck_server.domain.User.service.ReadAllUserListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserController {
    private final ReadAllUserListService readAllUserListService;
    private final TestCreateUserListService testCreateUserListService;

    @PostMapping("/create")
    public void testCreatePurpose(@RequestBody UserRequest userRequest) {
        testCreateUserListService.createUserList(userRequest);
    }

    @GetMapping("/all")
    public List<UserResponse> getAllUserList() {
        return readAllUserListService.readAllUserList();
    }
}
