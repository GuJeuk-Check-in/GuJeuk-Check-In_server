package com.example.gujeuck_server.domain.common.healthCheck.presentation.dto.response;

public record ReadyHealthResponse(
        String status,
        String db
) {
    public static ReadyHealthResponse up() {
        return new ReadyHealthResponse("UP", "UP");
    }

    public static ReadyHealthResponse down() {
        return new ReadyHealthResponse("DOWN", "DOWN");
    }

    public boolean hasUpStatus() {
        return "UP".equals(status) && "UP".equals(db);
    }
}
