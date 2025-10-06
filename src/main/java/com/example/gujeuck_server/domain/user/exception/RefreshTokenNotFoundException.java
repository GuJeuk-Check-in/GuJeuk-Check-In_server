<<<<<<< Updated upstream
package com.example.gujeuck_server.domain.user.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class RefreshTokenNotFoundException extends GujeukException {
    public static final GujeukException EXCEPTION = new RefreshTokenNotFoundException();

    public RefreshTokenNotFoundException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
=======
>>>>>>> Stashed changes
