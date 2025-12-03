package com.example.gujeuck_server.domain.admin.controller;

import com.example.gujeuck_server.domain.admin.dto.response.UseListResponse;
import com.example.gujeuck_server.domain.admin.dto.request.AdminRequest;
import com.example.gujeuck_server.domain.admin.dto.request.ChangePasswordRequest;
import com.example.gujeuck_server.domain.admin.dto.request.UseListRequest;
import com.example.gujeuck_server.domain.admin.service.etc.ChangePasswordService;
import com.example.gujeuck_server.domain.admin.service.etc.CreateAdminService;
import com.example.gujeuck_server.domain.admin.service.excel.LogExcelOutPutService;
import com.example.gujeuck_server.domain.admin.service.list.*;
import com.example.gujeuck_server.domain.admin.service.token.AdminLoginService;
import com.example.gujeuck_server.domain.admin.service.token.ReissueService;
import com.example.gujeuck_server.domain.user.dto.response.SliceWithTotalResponse;
import com.example.gujeuck_server.domain.user.dto.response.UserDto;
import com.example.gujeuck_server.domain.user.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final CreateUseListService createUseListService;
    private final ReadAllUseListService readAllUseListService;
    private final AdminLoginService adminLoginService;
    private final CreateAdminService createAdminService;
    private final ChangePasswordService changePasswordService;
    private final LogExcelOutPutService logExcelOutPutService;
    private final ReissueService reissueService;
    private final ReadAllUserListService readAllUserListService;
    private final DeleteUseListService deleteUseListService;
    private final UpdateUseListService updateUseListService;
    private final ReadAllUserListByResidenceService readAllUserListByResidenceService;
    private final ReadOneUseListService readOneUseListService;

    @PostMapping("/list/create")
    public void createUseList(@RequestBody @Valid UseListRequest useListRequest) {
        createUseListService.creatUseList(useListRequest);
    }

    @GetMapping("/user/all")
    public SliceWithTotalResponse<UserDto> getAllUserList(
            @PageableDefault(size = 30, sort = {"id"}, direction = Sort.Direction.DESC)
            Pageable pageable) {
        return readAllUserListService.readAllUserList(pageable);
    }

    @GetMapping("/user")
    public UserResponse getALlUserByResidenceList(@RequestParam String residence) {
        return readAllUserListByResidenceService.readAllUserListByResidence(residence);
    }

    @DeleteMapping("/list/{id}")
    public void deleteUseList(@PathVariable Long id) {
        deleteUseListService.deleteUseList(id);
    }

    @PatchMapping("/list/{id}")
    public void updateUseList(@PathVariable Long id, @RequestBody @Valid UseListRequest useListRequest) {
        updateUseListService.updateLog(id, useListRequest);
    }

    @GetMapping("/list/all")
    public Slice<UseListResponse> getAllUseList(
            @PageableDefault(size = 30, sort = {"visitDate", "id"}, direction = Sort.Direction.DESC)
            Pageable pageable) {
        return readAllUseListService.readAllUseList(pageable);
    }

    @GetMapping("/list/{id}")
    public UseListResponse getOneUseList(@PathVariable Long id) {
        return readOneUseListService.readOneUseList(id);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid AdminRequest request) {

        String accessToken = adminLoginService.login(request);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + accessToken)
                .build();
    }

    @PostMapping("/create")
    public void createAdmin(@RequestBody @Valid AdminRequest request) {
        createAdminService.createAdmin(request);
    }

    @PatchMapping("/change")
    public void changeAdmin(@RequestBody @Valid ChangePasswordRequest request) {
        changePasswordService.changePassword(request);
    }

    @GetMapping("/excel/{yearMonth}")
    public ResponseEntity<byte[]> exportExcel(@PathVariable String yearMonth) {
        return logExcelOutPutService.outputExcel(yearMonth);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/re-issue")
    public ResponseEntity<Void> reissue(@RequestHeader(name = "Authorization") String token) {

        String accessToken = reissueService.reissue(token);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + accessToken)
                .build();
    }
}
