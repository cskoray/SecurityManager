package com.solidcode.security.mappers;

import com.solidcode.security.dto.request.AccessRequestDto;
import com.solidcode.security.dto.response.AccessResponse;
import com.solidcode.security.dto.response.AccessResponse.AccessResponseBuilder;
import com.solidcode.security.enums.ProtocolType;
import com.solidcode.security.repository.entity.AccessControl;
import com.solidcode.security.repository.entity.AccessControl.AccessControlBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-10T12:54:59+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.4 (Oracle Corporation)"
)
@Component
public class AccessMapperImpl implements AccessMapper {

    @Override
    public AccessControl accessRequestDtoToAccessControl(AccessRequestDto accessRequest) {
        if ( accessRequest == null ) {
            return null;
        }

        AccessControlBuilder accessControl = AccessControl.builder();

        accessControl.firstSeen( mapTimestamp( accessRequest.getFirstSeen() ) );
        if ( accessRequest.getType() != null ) {
            accessControl.protocolType( Enum.valueOf( ProtocolType.class, accessRequest.getType() ) );
        }
        accessControl.value( accessRequest.getValue() );
        if ( accessRequest.getTotalCount() != null ) {
            accessControl.totalCount( Integer.parseInt( accessRequest.getTotalCount() ) );
        }

        return accessControl.build();
    }

    @Override
    public AccessResponse accessControlToAccessControlResponse(AccessControl accessControl) {
        if ( accessControl == null ) {
            return null;
        }

        AccessResponseBuilder accessResponse = AccessResponse.builder();

        accessResponse.firstSeen( mapTimestampResponse( accessControl.getFirstSeen() ) );
        accessResponse.value( accessControl.getValue() );
        accessResponse.totalCount( String.valueOf( accessControl.getTotalCount() ) );

        return accessResponse.build();
    }
}
