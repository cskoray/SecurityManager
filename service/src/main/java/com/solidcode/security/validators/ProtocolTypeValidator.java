package com.solidcode.security.validators;

import com.solidcode.security.enums.ProtocolType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProtocolTypeValidator implements
    ConstraintValidator<ValidProtocolType, String> {

  @Override
  public void initialize(ValidProtocolType constraintAnnotation) {
  }

  @Override
  public boolean isValid(String type, ConstraintValidatorContext context) {
    return (validateProtocolType(type));
  }

  private boolean validateProtocolType(String type) {

    for (ProtocolType protocolType : ProtocolType.values()) {
      if (protocolType.name().equalsIgnoreCase(type)) {
        return true;
      }
    }
    return false;
  }
}
