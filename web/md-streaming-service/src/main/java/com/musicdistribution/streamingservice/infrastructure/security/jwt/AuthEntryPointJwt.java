package com.musicdistribution.streamingservice.infrastructure.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicdistribution.streamingservice.constant.AuthConstants;
import com.musicdistribution.streamingservice.constant.ServletConstants;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * An interceptor which is used as a handler for unauthorized user requests.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    /**
     * Method used to handle the unauthorized requests.
     *
     * @param request       - the servlet request wrapper object.
     * @param response      - the servlet response wrapper object.
     * @param authException - the exception which is being thrown because of the unauthorized request.
     * @throws IOException - if an error occurs when returning the error
     *                     message to the user for the unauthorized request.
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put(ServletConstants.STATUS, HttpServletResponse.SC_UNAUTHORIZED);
        body.put(ServletConstants.ERROR, AuthConstants.UNAUTHORIZED_MESSAGE);
        body.put(ServletConstants.MESSAGE, authException.getMessage());
        body.put(ServletConstants.PATH, request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
