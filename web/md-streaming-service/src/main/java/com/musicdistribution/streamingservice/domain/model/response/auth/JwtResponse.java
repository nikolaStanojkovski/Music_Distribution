package com.musicdistribution.streamingservice.domain.model.response.auth;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Object used for JWT data transfer from the
 * back-end to the front-end for a user.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtResponse {

    private String jwtToken;
}
