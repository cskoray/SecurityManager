package com.solidcode.security.service;


import static com.solidcode.security.exception.ErrorType.DUPLICATE_ACCESS_VALUE_ERROR;
import static com.solidcode.security.exception.ErrorType.VALUE_NOT_FOUND;

import com.solidcode.security.dto.request.AccessRequestDto;
import com.solidcode.security.dto.response.AccessResponse;
import com.solidcode.security.exception.AccessControlException;
import com.solidcode.security.mappers.AccessMapper;
import com.solidcode.security.repository.AccessControlRepository;
import com.solidcode.security.repository.entity.AccessControl;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccessControlService {

  @Autowired
  private AccessValidationService validationService;

  @Autowired
  private AccessMapper accessMapper;

  @Autowired
  private AccessControlRepository accessControlRepository;

  public void recordAccess(AccessRequestDto accessRequest) {

    String value = accessRequest.getValue();
    validationService.validateAccessByValue(value)
        .ifPresentOrElse(found -> {
              log.info("Access record already exists with value {} ", value);
              throw new AccessControlException(DUPLICATE_ACCESS_VALUE_ERROR);
            }, () -> {
              AccessControl
                  accessControl = accessMapper.accessRequestDtoToAccessControl(accessRequest);
              accessControlRepository.save(accessControl);
              log.info("New access record saved exists with value {} ", value);
            }
        );
  }

  public AccessResponse getAccessByValue(String value) {

    Optional<AccessControl> access = validationService.validateAccessByValue(value);
    if (access.isPresent()) {

      log.info("Access record found with {} ", access.get());
      return accessMapper.accessControlToAccessControlResponse(access.get());
    } else {
      log.info("Access record not found with value {} ", value);
      throw new AccessControlException(VALUE_NOT_FOUND);
    }
  }
}
