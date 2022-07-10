package com.solidcode.security.dto.request;

import com.solidcode.security.validators.ValidIP;
import com.solidcode.security.validators.ValidProtocolType;
import com.solidcode.security.validators.ValidTotalCount;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessRequestDto {

  @NotNull
  @NotEmpty
  @ValidProtocolType
  private String type;

  @NotNull
  @NotEmpty
  @ValidIP
  private String value;

  @NotNull
  @NotEmpty
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private String firstSeen;

  @NotNull
  @NotEmpty
  @ValidTotalCount
  private String totalCount;
}