package com.musicdistribution.albumcatalog.domain.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {


    private final String token;
    private final Long id;
    private final String username;
    private final String email;
}