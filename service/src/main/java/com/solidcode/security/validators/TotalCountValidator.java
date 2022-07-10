package com.solidcode.security.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TotalCountValidator implements ConstraintValidator<ValidTotalCount, String> {

  @Override
  public void initialize(ValidTotalCount constraintAnnotation) {
  }

  @Override
  public boolean isValid(String totalCount, ConstraintValidatorContext context) {
    return (validateTotalCount(totalCount));
  }

  private boolean validateTotalCount(String totalCount) {

    try {
      int totalCountNumeric = Integer.parseInt(totalCount);
      if (totalCountNumeric >= 0 && totalCountNumeric <= 100) {
        return true;
      }
    } catch (NumberFormatException e) {
      return false;
    }
    return false;
  }
}
