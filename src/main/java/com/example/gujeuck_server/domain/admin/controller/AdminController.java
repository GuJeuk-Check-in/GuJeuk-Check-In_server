package com.example.gujeuck_server.domain.admin.controller;

import com.example.gujeuck_server.domain.admin.dto.UseListRequest;
import com.example.gujeuck_server.domain.admin.service.CreateUseListService;
import com.example.gujeuck_server.domain.admin.service.ReadAllUserListService;
import com.example.gujeuck_server.domain.admin.service.ReadOneUserListService;
import com.example.gujeuck_server.domain.user.dto.UserResponse;
<<<<<<< HEAD
import lombok.Getter;
=======
>>>>>>> origin/feat/admin-CreateUseList
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
<<<<<<< HEAD
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
=======
@RequestMapping("/admin/user")
public class AdminController {
    private final ReadAllUserListService readAllUserListService;
    private final ReadOneUserListService readOneUserListService;
    private final CreateUseListService createUseListService;

    @GetMapping("/all")
>>>>>>> origin/feat/admin-CreateUseList
    public List<UserResponse> readAllUserList() {
        return readAllUserListService.readAllUserList();
    }

<<<<<<< HEAD
    @GetMapping("/user/{id}")
    public UserResponse readUser(@PathVariable Long id) {
        return readOneUserListService.readOneUserList(id);
    }
=======
    @GetMapping("/{id}")
    public UserResponse readOneUserList(@PathVariable Long id) {
        return readOneUserListService.readOneUserList(id);
    }

    @PostMapping("/create")
    public void createUseList(@RequestBody UseListRequest useListRequest){
        createUseListService.createUseList(useListRequest);
    }
>>>>>>> origin/feat/admin-CreateUseList
}
