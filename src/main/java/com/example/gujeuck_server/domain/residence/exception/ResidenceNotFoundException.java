package com.example.gujeuck_server.domain.residence.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class ResidenceNotFoundException extends GujeukException {
  public static final GujeukException EXCEPTION = new ResidenceNotFoundException();

  private ResidenceNotFoundException() {
    super(ErrorCode.RESIDENCE_NOT_FOUND);
  }
}
