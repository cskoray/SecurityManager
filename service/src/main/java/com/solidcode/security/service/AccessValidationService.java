package com.solidcode.security.service;

import com.solidcode.security.repository.AccessControlRepository;
import com.solidcode.security.repository.entity.AccessControl;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessValidationService {

  @Autowired
  private AccessControlRepository accessControlRepository;

  public Optional<AccessControl> validateAccessByValue(String value) {

    return accessControlRepository.findByValue(value);
  }
}
