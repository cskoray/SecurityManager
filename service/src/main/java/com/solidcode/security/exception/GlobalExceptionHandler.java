package com.solidcode.security.exception;

import static com.solidcode.security.exception.ErrorType.INVALID_PARAMETER_ERROR;
import static java.util.Collections.singletonList;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(AccessControlException.class)
  @ResponseBody
  public ResponseEntity<ErrorList> handleException(AccessControlException exception) {

    Error error = createError(exception);
    return new ResponseEntity<>(createErrorList(singletonList(error)),
        exception.getErrorType().getHttpStatus());
  }

  private Error createError(AccessControlException exception) {
    return createError(exception.getErrorType(), exception.getField(), exception.getMessage());
  }

  private Error createError(ErrorType errorType, String field, String message) {

    return Error.builder()
        .code(errorType.getCode())
        .message(message)
        .field(field)
        .build();
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public final ResponseEntity<ErrorList> handleMessagingException(
      IllegalArgumentException exception) {
    return createSingleErrorListResponse(INVALID_PARAMETER_ERROR, exception, null, null);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public final ResponseEntity<ErrorList> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {

    ErrorType errorType = INVALID_PARAMETER_ERROR;
    List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
    List<Error> errorList = fieldErrors.stream()
        .map(fieldError -> createError(errorType, fieldError.getField(),
            fieldError.getDefaultMessage()))
        .collect(Collectors.toList());

    return new ResponseEntity<>(createErrorList(errorList), errorType.getHttpStatus());
  }

  private ErrorList createErrorList(List<Error> errorList) {
    return ErrorList.builder()
        .errors(errorList)
        .build();
  }

  private ResponseEntity<ErrorList> createSingleErrorListResponse(ErrorType errorType,
      Exception exception, String field, String message) {

    Error error = createError(errorType, field, message);
    return new ResponseEntity<>(createErrorList(singletonList(error)), errorType.getHttpStatus());
  }
}
