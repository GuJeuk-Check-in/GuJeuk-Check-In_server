package com.example.gujeuck_server.domain.common.presentation;

import com.example.gujeuck_server.domain.common.service.HealthCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonController {
    private final HealthCheckService healthCheckService;

    @GetMapping("/health")
    public String health() {
        return healthCheckService.healthCheck();
    }

}
