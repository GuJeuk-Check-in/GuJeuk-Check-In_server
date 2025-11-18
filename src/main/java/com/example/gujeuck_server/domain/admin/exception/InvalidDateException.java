package com.example.gujeuck_server.domain.admin.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class InvalidDateException extends GujeukException {
  public static final GujeukException EXCEPTION = new InvalidDateException();
  private InvalidDateException() {
    super(ErrorCode.INVALID_DATE);
  }
}
