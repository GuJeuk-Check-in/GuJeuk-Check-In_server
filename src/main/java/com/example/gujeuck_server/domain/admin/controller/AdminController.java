package com.example.gujeuck_server.domain.admin.controller;

import com.example.gujeuck_server.domain.admin.dto.UseListRequest;
import com.example.gujeuck_server.domain.admin.repository.AdminRepository;
import com.example.gujeuck_server.domain.admin.service.CreateUseListService;
import com.example.gujeuck_server.domain.admin.service.ReadAllUserListService;
import com.example.gujeuck_server.domain.admin.service.ReadOneUserListService;
import com.example.gujeuck_server.domain.purpose.service.ReadAllPurposeService;
import com.example.gujeuck_server.domain.purpose.service.ReadOnePurposeService;
import com.example.gujeuck_server.domain.user.dto.UserResponse;
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
    @PostMapping("/create")
    public void createUseList(@RequestBody UseListRequest useListRequest) {
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
}
