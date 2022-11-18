package com.musicdistribution.storageservice.util;

import com.musicdistribution.storageservice.config.security.jwt.JwtProperties;
import com.musicdistribution.storageservice.service.implementation.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * A utility helper class used for JWT token related processing.
 */
@Slf4j
@Component
@AllArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    /**
     * Method used to generate a new JWT token.
     *
     * @param authentication - an authentication object wrapper from which the user credentials are to be read from.
     * @return the generated JWT token.
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtProperties.getJwtExpirationMs()))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getJwtSecret())
                .compact();
    }

    /**
     * Method used to read a username based on the specified JWT token.
     *
     * @param token - the token from which the username is to read from.
     * @return the user's username.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getJwtSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Method used to validate a specific auth token.
     *
     * @param authToken - the authentication token to be validated as a JWT token.
     * @return a flag determining whether a JWT token is valid or not.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtProperties.getJwtSecret()).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}