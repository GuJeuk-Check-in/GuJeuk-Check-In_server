package com.example.burserker.gujeuk.infrastructure.excel.exception;

import com.example.burserker.gujeuk.global.error.exception.ErrorCode;
import com.example.burserker.gujeuk.global.error.exception.GujeukException;

public class InvalidDateException extends GujeukException {
  public static final GujeukException EXCEPTION = new InvalidDateException();
  private InvalidDateException() {
    super(ErrorCode.INVALID_DATE);
  }
}
