package com.solidcode.security.controller;

import static com.solidcode.security.constants.RestApiUrls.ACCESS_CONTROL;
import static com.solidcode.security.constants.RestApiUrls.GET_ACCESS_CONTROL_BY_VALUE;
import static com.solidcode.security.constants.RestApiUrls.RECORD;
import static org.springframework.http.ResponseEntity.ok;

import com.solidcode.security.dto.request.AccessRequestDto;
import com.solidcode.security.dto.response.AccessResponse;
import com.solidcode.security.service.AccessControlService;
import com.solidcode.security.validators.ValidIP;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(ACCESS_CONTROL)
@Slf4j
public class AccessController {

  @Autowired
  private AccessControlService accessControlService;

  @PostMapping(RECORD)
  public ResponseEntity<AccessResponse> record(
      @Valid @RequestBody AccessRequestDto accessRequest) {

    log.info("Attempting to record access {} ", accessRequest);
    accessControlService.recordAccess(accessRequest);
    return ok().build();
  }

  @GetMapping(GET_ACCESS_CONTROL_BY_VALUE)
  public ResponseEntity<AccessResponse> getAccessControlByValue(
      @Valid @PathVariable("value") @ValidIP String value) {

    log.info("Getting the access record with value {} ", value);
    AccessResponse access = accessControlService.getAccessByValue(value);
    return ok().body(access);
  }
}
