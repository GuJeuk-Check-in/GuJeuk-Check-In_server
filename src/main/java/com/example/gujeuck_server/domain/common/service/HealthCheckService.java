package com.example.gujeuck_server.domain.common.service;

import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {

    public String healthCheck() {
        return "UP";
    }
}
