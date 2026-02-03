package com.example.gujeuck_server.domain.organ.presentation;

import com.example.gujeuck_server.domain.organ.presentation.dto.request.CreateOrganRequest;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.OrganResponse;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.TokenResponse;
import com.example.gujeuck_server.domain.organ.presentation.dto.request.LoginOrganRequest;
import com.example.gujeuck_server.domain.organ.presentation.dto.request.ChangePasswordRequest;
import com.example.gujeuck_server.domain.organ.service.*;
import com.example.gujeuck_server.domain.user.presentation.dto.request.UpdateUserRequest;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organ")
public class OrganController {
    private final LoginOrganService loginOrganService;
    private final CreateOrganService createOrganService;
    private final ChangePasswordService changePasswordService;
    private final LogExcelOutPutService logExcelOutPutService;
    private final ReissueService reissueService;
    private final QueryUserListService queryUserListService;
    private final QueryUserListByResidenceService queryUserListByResidenceService;
    private final UpdateUserService updateUserService;
    private final QueryUserDetailService queryUserDetailService;
    private final QueryOrganNameListService queryOrganNameListService;

    @GetMapping("/user/all")
    public SliceWithTotalResponse<UserInfoResponse> queryAllUserList(
            @PageableDefault(size = 30, sort = {"id"}, direction = Sort.Direction.DESC)
            Pageable pageable) {
        return queryUserListService.execute(pageable);
    }

    @GetMapping("/user")
    public SliceWithTotalResponse<UserInfoResponse> queryAllUserByResidenceList(@RequestParam String residence, Pageable pageable) {
        return queryUserListByResidenceService.execute(residence, pageable);
    }

    @GetMapping("/user/{id}")
    public UserDetailResponse queryUserDetail(@PathVariable Long id) {
        return queryUserDetailService.execute(id);
    }

    @PatchMapping("/user/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest request) {
        updateUserService.execute(id, request);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginOrganRequest request) {
        return loginOrganService.execute(request);
    }

    @PostMapping("/create")
    public void createOrgan(@RequestBody @Valid CreateOrganRequest request) {
        createOrganService.execute(request);
    }

    @PatchMapping("/change")
    public void changeOrgan(@RequestBody @Valid ChangePasswordRequest request) {
        changePasswordService.execute(request);
    }

    @GetMapping("/excel/{yearMonth}")
    public ResponseEntity<byte[]> exportExcel(@PathVariable String yearMonth) {
        return logExcelOutPutService.execute(yearMonth);
    }

    @PatchMapping("/re-issue")
    public TokenResponse reissue(Authentication authentication) {
        return reissueService.execute(authentication);
    }

    @GetMapping("/name")
    public List<OrganResponse> queryOrganNameList() {
        return queryOrganNameListService.execute();
    }
}
