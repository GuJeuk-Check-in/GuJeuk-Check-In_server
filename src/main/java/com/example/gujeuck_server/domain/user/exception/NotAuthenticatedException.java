package com.example.gujeuck_server.domain.user.exception;

public class NotAuthenticatedException extends RuntimeException {
    public NotAuthenticatedException(String message) {
        super(message);
    }
}
