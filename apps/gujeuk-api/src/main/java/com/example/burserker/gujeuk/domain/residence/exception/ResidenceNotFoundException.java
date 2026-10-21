package com.example.burserker.gujeuk.domain.residence.exception;

import com.example.burserker.gujeuk.global.error.exception.ErrorCode;
import com.example.burserker.gujeuk.global.error.exception.GujeukException;

public class ResidenceNotFoundException extends GujeukException {
  public static final GujeukException EXCEPTION = new ResidenceNotFoundException();

  private ResidenceNotFoundException() {
    super(ErrorCode.RESIDENCE_NOT_FOUND);
  }
}
