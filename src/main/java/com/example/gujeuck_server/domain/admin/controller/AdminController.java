package com.example.gujeuck_server.domain.admin.controller;

import com.example.gujeuck_server.domain.admin.dto.request.AdminRequest;
import com.example.gujeuck_server.domain.admin.dto.request.ChangePasswordRequest;
import com.example.gujeuck_server.domain.admin.dto.request.UseListRequest;
import com.example.gujeuck_server.domain.admin.service.list.*;
import com.example.gujeuck_server.domain.admin.service.token.AdminLoginService;
import com.example.gujeuck_server.domain.admin.service.etc.ChangePasswordService;
import com.example.gujeuck_server.domain.admin.service.etc.CreateAdminService;
import com.example.gujeuck_server.domain.user.dto.UserResponse;
import com.example.gujeuck_server.domain.user.dto.response.TokenResponse;
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
    private final AdminLoginService adminLoginService;
    private final CreateAdminService createAdminService;
    private final ChangePasswordService changePasswordService;

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

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid AdminRequest request) {
        return adminLoginService.login(request);
    }

    @PostMapping("/create")
    public void createAdmin(@RequestBody @Valid AdminRequest request) {
        createAdminService.createAdmin(request);
    }

    @PatchMapping("/change")
    public void changeAdmin(@RequestBody @Valid ChangePasswordRequest request) {
        changePasswordService.changePassword(request);
    }
}
