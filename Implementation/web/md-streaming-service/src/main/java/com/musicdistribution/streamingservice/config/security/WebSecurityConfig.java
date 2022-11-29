package com.musicdistribution.streamingservice.config.security;

import com.musicdistribution.streamingservice.config.security.jwt.AuthEntryPointJwt;
import com.musicdistribution.streamingservice.config.security.jwt.AuthTokenFilter;
import com.musicdistribution.streamingservice.constant.AuthConstants;
import com.musicdistribution.streamingservice.constant.PathConstants;
import com.musicdistribution.streamingservice.service.implementation.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The main configuration for the web security mechanisms.
 */
@Configuration
@AllArgsConstructor
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    private final AuthEntryPointJwt unauthorizedHandler;

    /**
     * Method used for assigning the appropriate filter class which handles the JWT authentication.
     *
     * @return the filter that handles the JWT authentication.
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    /**
     * Method used for assigning the custom authentication provider.
     *
     * @return the custom configured DAO authentication provider.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * Method used for assigning the appropriate authentication manager.
     *
     * @param authConfig - the authentication configuration helper class.
     * @return the configured authentication manager.
     * @throws Exception if the authentication manager was not properly read from the authentication configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Method used for assigning the appropriate password encoder.
     *
     * @return the custom configured password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Method used for configuration of the entire filter security chain.
     *
     * @param http - a reference to the security wrapper object which provides configuration options.
     * @return the custom configured security filter chain.
     * @throws Exception if the filter chain was not properly configured.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers(AuthConstants.AUTHENTICATION_ANT_MATCHER).permitAll()
                .antMatchers(PathConstants.API_EMAIL_DOMAINS,
                        PathConstants.API_ALBUMS, PathConstants.API_ALBUMS_SEARCH,
                        PathConstants.API_ARTISTS, PathConstants.API_ARTISTS_SEARCH,
                        PathConstants.API_SONGS, PathConstants.API_SONGS_SEARCH,
                        PathConstants.API_LISTENERS, PathConstants.API_LISTENERS_SEARCH,
                        PathConstants.API_NOTIFICATIONS, PathConstants.API_NOTIFICATIONS_SEARCH,
                        AuthConstants.STREAM_ANT_MATCHER).permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}