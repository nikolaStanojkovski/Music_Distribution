package com.musicdistribution.streamingservice.config.security.jwt;

import com.musicdistribution.streamingservice.constant.PropertyConstants;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Configuration helper class used for storing static JWT properties.
 */
@Getter
@Component
public class JwtProperties {

    @Value(PropertyConstants.MD_JWT_SECRET)
    private String jwtSecret;

    @Value(PropertyConstants.MD_JWT_EXPIRATION_TIME)
    private Integer jwtExpirationMs;
}
