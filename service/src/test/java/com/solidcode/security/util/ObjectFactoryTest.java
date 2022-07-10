package com.solidcode.security.util;

import static java.sql.Timestamp.valueOf;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solidcode.security.dto.request.AccessRequestDto;
import com.solidcode.security.dto.response.AccessResponse;
import com.solidcode.security.enums.ProtocolType;
import com.solidcode.security.repository.entity.AccessControl;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ObjectFactoryTest {

  public static final String ACCESS_CONTROL = "/api/v1/access-control";
  public static final String RECORD = "/record";
  public static final String GET_ACCESS_CONTROL_BY_VALUE = "/{value}/value";

  public static final String ERROR_CODE = "$.errors[0].code";
  public static final String ERROR_DESCRIPTION = "$.errors[0].message";

  public final static String TYPE = "ipv4";
  public final static String VALUE = "1.2.3.4";
  public final static String VALUE_INVALID = "1.2.3.4444";
  public final static DateTimeFormatter FORMATTER = ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
  public final static LocalDateTime FIRST_SEEN_TS = parse("2020-07-10 15:00:00.000", FORMATTER);
  public final static String FIRST_SEEN = "2020-07-10 15:00:00.000";
  public final static String FIRST_SEEN_INVALID = "some date";
  public final static String TOTAL_COUNT = "1";
  public final static String TOTAL_COUNT_INVALID = "-1";
  public final static String INVALID_TOTAL_COUNT_MESSAGE = "TotalCount cannot be less than 0 and cannot be more than 100.";
  public final static String INVALID_VALUE_MESSAGE = "Value must be within range 000.000.000.000 - 255.255.255.255";

  private static final ObjectMapper mapper = new ObjectMapper();

  public static AccessRequestDto BuildAccessRequest(String value, String firstSeen,
      String totalCount) {

    return AccessRequestDto.builder()
        .type(TYPE)
        .value(value)
        .firstSeen(firstSeen)
        .totalCount(totalCount)
        .build();
  }

  public static AccessResponse buildAccessResponse() {

    return AccessResponse.builder()
        .type(TYPE)
        .value(VALUE)
        .firstSeen(FIRST_SEEN)
        .totalCount(TOTAL_COUNT)
        .build();
  }

  public static AccessControl BuildAccessControl() {

    return AccessControl.builder()
        .firstSeen(valueOf(FIRST_SEEN))
        .protocolType(ProtocolType.valueOf(TYPE))
        .totalCount(Integer.parseInt(TOTAL_COUNT))
        .value(VALUE)
        .build();
  }

  public static String ObjectToJson(Object jsonObject) {
    String json;
    if (jsonObject == null) {
      json = "Null Object";
    } else {
      try {
        json = mapper.writeValueAsString(jsonObject);
      } catch (Exception e) {
        json = "Object could not be converted to Json Format";
      }
    }
    return json;
  }
}
