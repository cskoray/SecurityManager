package com.solidcode.security.repository;

import static com.solidcode.security.util.ObjectFactoryTest.BuildAccessControl;
import static com.solidcode.security.util.ObjectFactoryTest.VALUE;
import static com.solidcode.security.util.ObjectFactoryTest.VALUE_INVALID;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.solidcode.security.repository.entity.AccessControl;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AccessControlRepositoryTest {

  @Autowired
  private AccessControlRepository unit;

  @BeforeEach
  void setUp() {

    AccessControl accessControl = BuildAccessControl();
    unit.save(accessControl);
  }

  @AfterEach
  void tearDown() {
    unit.deleteAll();
  }

  @Test
  public void itShouldFindByValue() {

    Optional<AccessControl> expected = unit.findByValue(VALUE);

    assertThat(of(expected), notNullValue());
  }

  @Test
  public void itShouldFindByValue_DoesNotExist() {

    Optional<AccessControl> expected = unit.findByValue(VALUE_INVALID);

    assertFalse(expected.isPresent());
  }
}