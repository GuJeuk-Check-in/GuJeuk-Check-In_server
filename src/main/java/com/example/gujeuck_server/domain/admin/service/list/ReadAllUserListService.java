package com.example.gujeuck_server.domain.admin.service.list;

import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.user.dto.response.UserResponse;
import com.example.gujeuck_server.domain.user.entity.enums.Residence;
import com.example.gujeuck_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadAllUserListService {
    private final UserRepository userRepository;
    private final AdminFacade adminFacade;

    @Transactional(readOnly = true)
    public Slice<UserResponse> readAllUserList(Pageable p) {
        adminFacade.currentUser();

        Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        return userRepository.findAllBy(pageable)
                .map(UserResponse::from);
    }
}
