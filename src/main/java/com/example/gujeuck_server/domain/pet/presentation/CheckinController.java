package com.example.gujeuck_server.domain.pet.presentation;

import com.example.gujeuck_server.domain.pet.presentation.dto.response.CheckinResponse;
import com.example.gujeuck_server.domain.pet.service.CheckinPetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CheckinController {
    private final CheckinPetService checkinPetService;

    @PostMapping("/checkin")
    public CheckinResponse checkin() {
        return checkinPetService.execute();
    }
}
