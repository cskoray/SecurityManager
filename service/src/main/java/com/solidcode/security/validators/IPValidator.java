package com.solidcode.security.validators;

import static java.util.regex.Pattern.compile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IPValidator implements
    ConstraintValidator<ValidIP, String> {

  private Pattern pattern;
  private Matcher matcher;
  private String IPV4_PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

  @Override
  public void initialize(ValidIP constraintAnnotation) {
  }

  @Override
  public boolean isValid(String ip, ConstraintValidatorContext context) {
    return validateIPV4(ip);
  }

  private boolean validateIPV4(String ipv4) {

    pattern = compile(IPV4_PATTERN);
    matcher = pattern.matcher(ipv4);
    return matcher.matches();
  }
}
