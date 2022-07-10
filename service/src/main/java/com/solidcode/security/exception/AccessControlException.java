package com.solidcode.security.exception;

import lombok.Getter;

@Getter
public class AccessControlException extends RuntimeException {

  private ErrorType errorType;
  private String field;
  private String code;

  public AccessControlException(ErrorType errorType) {
    super(errorType.getMessage());
    this.errorType = errorType;
  }

  public AccessControlException(ErrorType errorType, String field) {
    super(errorType.getMessage());
    this.code = errorType.getCode();
    this.errorType = errorType;
    this.field = field;
  }
}
