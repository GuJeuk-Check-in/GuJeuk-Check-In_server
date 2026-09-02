package com.example.gujeuck_server.domain.organ.presentation;

import com.example.gujeuck_server.domain.organ.presentation.dto.request.CreateOrganRequest;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.SystemUsageResponse;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.TokenResponse;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.VisitStatisticsResponse;
import com.example.gujeuck_server.domain.organ.presentation.dto.request.LoginOrganRequest;
import com.example.gujeuck_server.domain.organ.presentation.dto.request.ChangePasswordRequest;
import com.example.gujeuck_server.domain.organ.service.*;
import com.example.gujeuck_server.domain.user.presentation.dto.request.UpdateUserRequest;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserSliceWithTotalResponse;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserDetailResponse;
import com.example.gujeuck_server.domain.user.service.QueryUserDetailService;
import com.example.gujeuck_server.domain.residence.service.QueryUserListByResidenceService;
import com.example.gujeuck_server.domain.user.service.QueryUserListService;
import com.example.gujeuck_server.domain.user.service.UpdateUserService;
import com.example.gujeuck_server.global.security.auth.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/organ")
public class OrganController {
    private final LoginOrganService loginOrganService;
    private final CreateOrganService createOrganService;
    private final ChangePasswordService changePasswordService;
    private final LogExcelOutPutService logExcelOutPutService;
    private final UserExcelOutPutService userExcelOutPutService;
    private final ReissueService reissueService;
    private final QueryUserListService queryUserListService;
    private final QueryUserListByResidenceService queryUserListByResidenceService;
    private final UpdateUserService updateUserService;
    private final QueryUserDetailService queryUserDetailService;
    private final QueryVisitStatisticsService queryVisitStatisticsService;
    private final SystemUsageService systemUsageService;

    @GetMapping("/user/all")
    public UserSliceWithTotalResponse queryAllUserList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PageableDefault(size = 30, sort = {"id"}, direction = Sort.Direction.DESC)
            Pageable pageable) {
        return queryUserListService.execute(userDetails.organ().getId(), pageable);
    }

    @GetMapping("/user")
    public UserSliceWithTotalResponse queryAllUserByResidenceList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam String residence,
            Pageable pageable
    ) {
        return queryUserListByResidenceService.execute(userDetails.organ().getId(), residence, pageable);
    }

    @GetMapping("/user/{id}")
    public UserDetailResponse queryUserDetail(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id
    ) {
        return queryUserDetailService.execute(userDetails.organ().getId(), id);
    }

    @PatchMapping("/user/{id}")
    public void updateUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserRequest request
    ) {
        updateUserService.execute(userDetails.organ().getId(), id, request);
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
    public void changeOrgan(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid ChangePasswordRequest request
    ) {
        changePasswordService.execute(userDetails.organ().getId(), request);
    }

    @GetMapping("/excel/log/{yearMonth}")
    public ResponseEntity<byte[]> exportLogExcel(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable String yearMonth
    ) {
        return logExcelOutPutService.execute(userDetails.organ().getId(), yearMonth);
    }

    @GetMapping("/excel/user")
    public ResponseEntity<byte[]> exportUserExcel(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userExcelOutPutService.execute(userDetails.organ().getId());
    }

    @PatchMapping("/re-issue")
    public TokenResponse reissue(Authentication authentication) {
        return reissueService.execute(authentication);
    }

    @GetMapping("/statistics/visits")
    public VisitStatisticsResponse queryVisitStatistics(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return queryVisitStatisticsService.execute(userDetails.organ().getId(), year, month);
    }

    @GetMapping("/usage")
    public SystemUsageResponse querySystemUsage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam int year
    ) {
        return systemUsageService.execute(userDetails.organ().getId(), year);
    }
}
