package com.example.gujeuck_server.infrastructure.s3.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class ImageNotFoundException extends GujeukException {
    public static final GujeukException EXCEPTION = new ImageNotFoundException();

    public ImageNotFoundException() {
        super(ErrorCode.IMAGE_NOT_FOUND);
    }
}
