package com.solidcode.security.repository.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

import com.solidcode.security.enums.ProtocolType;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "access_control")
public class AccessControl implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private long id;

  @Column(name = "type", nullable = false)
  @Enumerated(STRING)
  private ProtocolType protocolType;

  @Column(name = "ip_value", nullable = false)
  private String value;

  @Column(name = "first_seen", updatable = false, nullable = false)
  private Timestamp firstSeen;

  @Column(name = "total_count", nullable = false)
  private int totalCount;
}
