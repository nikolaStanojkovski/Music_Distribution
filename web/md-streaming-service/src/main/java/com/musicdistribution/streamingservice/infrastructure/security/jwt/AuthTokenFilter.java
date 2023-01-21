package com.musicdistribution.streamingservice.infrastructure.security.jwt;

import com.musicdistribution.streamingservice.constant.AuthConstants;
import com.musicdistribution.streamingservice.constant.ServletConstants;
import com.musicdistribution.streamingservice.application.service.implementation.UserDetailsServiceImpl;
import com.musicdistribution.streamingservice.util.AuthUtil;
import com.musicdistribution.streamingservice.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * An authentication filter which checks the validity of a user request.
 */
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Method that handles the logic for validating a user JWT token.
     *
     * @param request     - the servlet request wrapper object.
     * @param response    - the servlet response wrapper object.
     * @param filterChain - a reference to the other filters that need to handle the user request.
     * @throws ServletException - if the filter did not complete the verification of the request successfully.
     * @throws IOException      - if an error occurred when trying to handle the authentication object for the response.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
                String username = jwtUtil.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(
                        AuthUtil.formatUsernamePrincipal(username,
                                request.getHeader(AuthConstants.AUTH_ROLE)));
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Method that parses the JWT token from a user request.
     *
     * @param request - the servlet request wrapper object.
     * @return the extracted JWT token from the request.
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(ServletConstants.AUTH_HEADER);

        if (StringUtils.hasText(headerAuth)
                && headerAuth.startsWith(String.format("%s ", AuthConstants.JWT_TOKEN_PREFIX))) {
            return headerAuth.substring(AuthConstants.JWT_TOKEN_LENGTH);
        }

        return null;
    }
}
