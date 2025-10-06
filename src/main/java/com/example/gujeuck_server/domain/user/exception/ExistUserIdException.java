<<<<<<< Updated upstream
package com.example.gujeuck_server.domain.user.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class ExistUserIdException extends GujeukException {
    public static final GujeukException EXCEPTION = new ExistUserIdException();

    private ExistUserIdException() {
        super(ErrorCode.USER_EXIST);
    }
}
=======
>>>>>>> Stashed changes
