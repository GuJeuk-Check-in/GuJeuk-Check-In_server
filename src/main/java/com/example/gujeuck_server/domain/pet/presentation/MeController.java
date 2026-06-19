package com.example.gujeuck_server.domain.pet.presentation;

import com.example.gujeuck_server.domain.pet.presentation.dto.response.MeResponse;
import com.example.gujeuck_server.domain.pet.service.QueryMeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MeController {
    private final QueryMeService queryMeService;

    @GetMapping("/me")
    public MeResponse queryMe() {
        return queryMeService.execute();
    }
}
