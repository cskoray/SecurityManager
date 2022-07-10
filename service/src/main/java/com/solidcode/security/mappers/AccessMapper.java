package com.solidcode.security.mappers;


import static com.solidcode.security.exception.ErrorType.DATE_TIME_FORMAT_ERROR;

import com.solidcode.security.dto.request.AccessRequestDto;
import com.solidcode.security.dto.response.AccessResponse;
import com.solidcode.security.exception.AccessControlException;
import com.solidcode.security.repository.entity.AccessControl;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AccessMapper {

  @Mapping(target = "firstSeen", source = "accessRequest.firstSeen", dateFormat = "yyyy-MM-dd HH:mm:ss.SSS", qualifiedBy = MapToTimestamp.class)
  @Mapping(target = "protocolType", source = "accessRequest.type")
  AccessControl accessRequestDtoToAccessControl(AccessRequestDto accessRequest);

  @Mapping(target = "firstSeen", source = "firstSeen", dateFormat = "yyyy-MM-dd HH:mm:ss.SSS", qualifiedBy = MapTimestampResponse.class)
  AccessResponse accessControlToAccessControlResponse(AccessControl accessControl);

  @MapToTimestamp
  default Timestamp mapTimestamp(String date) {
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
      Date parsedDate = dateFormat.parse(date);
      return new Timestamp(parsedDate.getTime());
    } catch (Exception e) {
      throw new AccessControlException(DATE_TIME_FORMAT_ERROR);
    }
  }

  @MapTimestampResponse
  default String mapTimestampResponse(Timestamp ts) {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    Date date = new Date();
    date.setTime(ts.getTime());
    return dateFormat.format(date);
  }
}
