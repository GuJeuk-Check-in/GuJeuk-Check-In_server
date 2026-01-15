package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserSliceWithTotalResponse;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserInfoResponse;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryUserListService {
    private final UserRepository userRepository;
    private final AdminFacade adminFacade;

    @Transactional(readOnly = true)
    public UserSliceWithTotalResponse<UserInfoResponse> readAllUserList(Pageable p) {
        adminFacade.currentUser();

        Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        long total = userRepository.count();

        Slice<UserInfoResponse> slice = userRepository.findAllBy(pageable)
                .map(UserInfoResponse::from);

        return new UserSliceWithTotalResponse<>(total, slice);
    }
}