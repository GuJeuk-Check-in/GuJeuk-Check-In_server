package com.example.gujeuck_server.domain.admin.presentation;

import com.example.gujeuck_server.domain.admin.presentation.dto.response.TokenResponse;
import com.example.gujeuck_server.domain.admin.presentation.dto.request.AdminRequest;
import com.example.gujeuck_server.domain.admin.presentation.dto.request.ChangePasswordRequest;
import com.example.gujeuck_server.domain.admin.service.ChangePasswordService;
import com.example.gujeuck_server.domain.admin.service.CreateAdminService;
import com.example.gujeuck_server.domain.admin.service.LogExcelOutPutService;
import com.example.gujeuck_server.domain.admin.service.LoginAdminService;
import com.example.gujeuck_server.domain.admin.service.ReissueService;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserSliceWithTotalResponse;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserDetailResponse;
import com.example.gujeuck_server.domain.user.service.QueryUserDetailService;
import com.example.gujeuck_server.domain.user.service.QueryUserListByResidenceService;
import com.example.gujeuck_server.domain.user.service.QueryUserListService;
import com.example.gujeuck_server.domain.user.service.UpdateUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final LoginAdminService loginAdminService;
    private final CreateAdminService createAdminService;
    private final ChangePasswordService changePasswordService;
    private final LogExcelOutPutService logExcelOutPutService;
    private final ReissueService reissueService;
    private final QueryUserListService queryUserListService;
    private final QueryUserListByResidenceService queryUserListByResidenceService;
    private final UpdateUserService updateUserService;
    private final QueryUserDetailService queryUserDetailService;
  
    @GetMapping("/user/all")
    public UserSliceWithTotalResponse getAllUserList(
            @PageableDefault(size = 30, sort = {"id"}, direction = Sort.Direction.DESC)
            Pageable pageable) {
        return queryUserListService.readAllUserList(pageable);
    }

    @GetMapping("/user")
    public UserSliceWithTotalResponse getALlUserByResidenceList(@RequestParam String residence, Pageable pageable) {
        return queryUserListByResidenceService.readAllUserListByResidence(residence, pageable);
    }

    @GetMapping("/user/{id}")
    public UserDetailResponse getUserDetail(@PathVariable Long id) {
        return queryUserDetailService.execute(id);
    }

    @PatchMapping("/user/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest request) {
        updateUserService.execute(id, request);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid AdminRequest request) {
        return loginAdminService.login(request);
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

    @PatchMapping("/re-issue")
    public TokenResponse reissue(Authentication authentication) {
        return reissueService.reissue(authentication);
    }
}
