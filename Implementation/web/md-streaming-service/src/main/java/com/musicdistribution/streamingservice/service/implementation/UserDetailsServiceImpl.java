package com.musicdistribution.streamingservice.service.implementation;

import com.musicdistribution.streamingservice.constant.AuthConstants;
import com.musicdistribution.streamingservice.constant.ExceptionConstants;
import com.musicdistribution.streamingservice.domain.model.enums.AuthRole;
import com.musicdistribution.streamingservice.domain.repository.ArtistRepository;
import com.musicdistribution.streamingservice.domain.repository.ListenerRepository;
import com.musicdistribution.streamingservice.domain.valueobject.UserRegistrationInfo;
import com.musicdistribution.streamingservice.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of the user details service.
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ArtistRepository artistRepository;
    private final ListenerRepository listenerRepository;

    /**
     * Method used to load a user by his username.
     *
     * @param usernameAndUserRole - the username and auth role by which the filtering is to be done.
     * @return an object containing the required detailed information about a user.
     * @throws UsernameNotFoundException if the user with the specified username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String usernameAndUserRole) throws UsernameNotFoundException {
        return Optional.of(getUserDetails(usernameAndUserRole))
                .map(ude -> UserDetailsImpl.build(ude.getKey(), ude.getValue()))
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(ExceptionConstants.USER_NOT_FOUND,
                                getUsername(usernameAndUserRole))));
    }

    /**
     * Method used to fetch the appropriate details for a user.
     *
     * @param usernameAndUserRole - the username and auth role by which the filtering is to be done.
     * @return a map entry containing the ID as a key and registration information for a user as value.
     * @throws UsernameNotFoundException if the user with the specified username is not found.
     */
    private Map.Entry<String, UserRegistrationInfo> getUserDetails(String usernameAndUserRole) {
        String username = getUsername(usernameAndUserRole);
        switch (getUserAuthRole(usernameAndUserRole)) {
            case ARTIST:
                return artistRepository.findByUserRegistrationInfo_Username(username)
                        .map(a -> new AbstractMap.SimpleEntry<>(a.getId().getId(), a.getUserRegistrationInfo()))
                        .orElseThrow(() -> new UsernameNotFoundException(
                                String.format(ExceptionConstants.ARTIST_USERNAME_NOT_FOUND, username)));
            case LISTENER:
                return listenerRepository.findByUserRegistrationInfo_Username(username)
                        .map(l -> new AbstractMap.SimpleEntry<>(l.getId().getId(), l.getUserRegistrationInfo()))
                        .orElseThrow(() -> new UsernameNotFoundException(
                                String.format(ExceptionConstants.LISTENER_USERNAME_NOT_FOUND, username)));
            default:
                return new AbstractMap.SimpleEntry<>(StringUtils.EMPTY,
                        UserRegistrationInfo.from(StringUtils.EMPTY, StringUtils.EMPTY));
        }
    }

    /**
     * Method used to extract the username from a specific string.
     *
     * @param usernameAndUserRole - the string containing the username and auth role for a specific user.
     * @return a string representation of the extracted username.
     */
    private String getUsername(String usernameAndUserRole) {
        return Optional.ofNullable(usernameAndUserRole)
                .map(uur -> uur.substring(0, uur.lastIndexOf(AuthConstants.USERNAME_AUTH_ROLE_DELIMITER)))
                .orElse(StringUtils.EMPTY);
    }

    /**
     * Method used to extract the auth role from a specific string.
     *
     * @param usernameAndUserRole - the string containing the username and auth role for a specific user.
     * @return the authentication role for a specific user.
     */
    private AuthRole getUserAuthRole(String usernameAndUserRole) {
        return Optional.ofNullable(usernameAndUserRole)
                .map(uur -> uur.substring(uur.lastIndexOf(AuthConstants.USERNAME_AUTH_ROLE_DELIMITER) + 1))
                .map(AuthUtil::parseAuthRole)
                .orElse(AuthRole.NONE);
    }
}
