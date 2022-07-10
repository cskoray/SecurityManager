package com.solidcode.security.repository;

import com.solidcode.security.repository.entity.AccessControl;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessControlRepository extends JpaRepository<AccessControl, String> {

  Optional<AccessControl> findByValue(String value);
}
