package com.example.gujeuck_server.domain.user.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class ExpiredTokenException extends GujeukException {
  public static final GujeukException EXCEPTION = new ExpiredTokenException();

  public ExpiredTokenException() {
    super(ErrorCode.EXPIRED_TOKEN);
  }
}
