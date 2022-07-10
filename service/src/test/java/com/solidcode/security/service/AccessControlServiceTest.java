package com.solidcode.security.service;

import static com.solidcode.security.enums.ProtocolType.ipv4;
import static com.solidcode.security.exception.ErrorType.DUPLICATE_ACCESS_VALUE_ERROR;
import static com.solidcode.security.exception.ErrorType.VALUE_NOT_FOUND;
import static com.solidcode.security.util.ObjectFactoryTest.BuildAccessControl;
import static com.solidcode.security.util.ObjectFactoryTest.BuildAccessRequest;
import static com.solidcode.security.util.ObjectFactoryTest.FIRST_SEEN;
import static com.solidcode.security.util.ObjectFactoryTest.TOTAL_COUNT;
import static com.solidcode.security.util.ObjectFactoryTest.VALUE;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.solidcode.security.dto.request.AccessRequestDto;
import com.solidcode.security.dto.response.AccessResponse;
import com.solidcode.security.exception.AccessControlException;
import com.solidcode.security.mappers.AccessMapper;
import com.solidcode.security.repository.AccessControlRepository;
import com.solidcode.security.repository.entity.AccessControl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;

@ExtendWith(MockitoExtension.class)
@ComponentScan({"com.solidcode.*"})
class AccessControlServiceTest {

  @Spy
  @InjectMocks
  private AccessControlService unit;

  @Mock
  private AccessValidationService validationService;

  @Mock
  private AccessMapper accessMapper;

  @Mock
  private AccessControlRepository accessControlRepository;

  @AfterEach
  void tearDown() {
    accessControlRepository.deleteAll();
  }

  @Test
  void record() {

    AccessRequestDto accessRequestDto = BuildAccessRequest(VALUE, FIRST_SEEN, TOTAL_COUNT);
    AccessControl accessControl = BuildAccessControl();
    when(validationService.validateAccessByValue(VALUE)).thenReturn(empty());
    when(accessMapper.accessRequestDtoToAccessControl(accessRequestDto)).thenReturn(accessControl);

    unit.recordAccess(accessRequestDto);

    ArgumentCaptor<AccessControl> accessControlArgumentCaptor = forClass(AccessControl.class);
    verify(accessControlRepository).save(accessControlArgumentCaptor.capture());
    verify(accessControlRepository, times(1)).save(accessControl);
    assertEquals(ipv4, accessControlArgumentCaptor.getValue().getProtocolType());
    assertEquals(VALUE, accessControlArgumentCaptor.getValue().getValue());
  }

  @Test
  void record_DuplicateValue() {

    AccessRequestDto accessRequestDto = BuildAccessRequest(VALUE, FIRST_SEEN, TOTAL_COUNT);
    AccessControl accessControl = BuildAccessControl();
    when(validationService.validateAccessByValue(VALUE)).thenReturn(of(accessControl));

    AccessControlException thrown = assertThrows(AccessControlException.class, () -> {
      unit.recordAccess(accessRequestDto);
    });

    assertThat(thrown.getErrorType(), is(DUPLICATE_ACCESS_VALUE_ERROR));
  }

  @Test
  public void getAccessByValue() {

    when(validationService.validateAccessByValue(VALUE)).thenReturn(of(BuildAccessControl()));

    AccessResponse accessByValue = unit.getAccessByValue(VALUE);

    assertThat(accessByValue, not(empty()));
  }

  @Test
  public void getAccessByValue_Not_Found() {

    when(validationService.validateAccessByValue(VALUE)).thenReturn(empty());

    AccessControlException thrown = assertThrows(AccessControlException.class, () -> {
      unit.getAccessByValue(VALUE);
    });

    assertThat(thrown.getErrorType(), is(VALUE_NOT_FOUND));
  }
}