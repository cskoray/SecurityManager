package com.solidcode.security.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessResponse {

  private String type;
  private String value;
  private String firstSeen;
  private String totalCount;
}
