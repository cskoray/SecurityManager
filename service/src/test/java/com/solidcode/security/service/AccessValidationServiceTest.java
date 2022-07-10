package com.solidcode.security.service;

import static com.solidcode.security.util.ObjectFactoryTest.BuildAccessControl;
import static com.solidcode.security.util.ObjectFactoryTest.VALUE;
import static com.solidcode.security.util.ObjectFactoryTest.VALUE_INVALID;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;

import com.solidcode.security.repository.AccessControlRepository;
import com.solidcode.security.repository.entity.AccessControl;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;

@ExtendWith(MockitoExtension.class)
@ComponentScan({"com.solidcode.*"})
class AccessValidationServiceTest {

  @InjectMocks
  private AccessValidationService unit;

  @Mock
  private AccessControlRepository accessControlRepository;

  @Test
  public void getAccessByValue() {

    when(accessControlRepository.findByValue(VALUE)).thenReturn(of(BuildAccessControl()));

    Optional<AccessControl> expected = unit.validateAccessByValue(VALUE);

    assertThat(expected.get(), not(empty()));
  }

  @Test
  public void getAccessByValue_Not_Found() {

    when(accessControlRepository.findByValue(VALUE_INVALID)).thenReturn(empty());

    Optional<AccessControl> expected = unit.validateAccessByValue(VALUE_INVALID);

    assertThat(expected, is(empty()));
  }
}