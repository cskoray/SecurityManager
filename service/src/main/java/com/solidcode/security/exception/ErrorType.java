package com.solidcode.security.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorType {

  INTERNAL_ERROR("10000", "An internal server error occurred", INTERNAL_SERVER_ERROR),
  DUPLICATE_ACCESS_VALUE_ERROR("10001", "Access values cannot be same.", BAD_REQUEST),
  VALUE_NOT_FOUND("10002", "Value cannot be found.", NOT_FOUND),
  INVALID_PARAMETER_ERROR("10003", "Invalid field(s)", BAD_REQUEST),
  DATE_TIME_FORMAT_ERROR("10004", "Date time has to be ISO8601 format. ", BAD_REQUEST);

  private String code;
  private String message;
  private HttpStatus httpStatus;
}
