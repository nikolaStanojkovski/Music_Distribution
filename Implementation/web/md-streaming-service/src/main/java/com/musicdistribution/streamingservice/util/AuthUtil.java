package com.musicdistribution.streamingservice.util;

import com.musicdistribution.streamingservice.constant.AuthConstants;
import com.musicdistribution.streamingservice.domain.model.enums.AuthRole;

import java.util.Optional;

/**
 * A utility helper class used for authentication related manipulation.
 */
public final class AuthUtil {

    /**
     * Private constructor which throws an exception because the class should not be instantiated.
     */
    private AuthUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Method used to format the username and authentication role for a given user.
     *
     * @param username - the username used for formatting.
     * @param authRole - the authentication role for a specific user.
     * @return the formatted username principal in a string representation.
     */
    public static String formatUsernamePrincipal(String username, String authRole) {
        return String.format("%s%s%s", username,
                AuthConstants.USERNAME_AUTH_ROLE_DELIMITER,
                parseAuthRole(authRole));
    }

    /**
     * Method used to parse the authentication role of a user.
     *
     * @param authRole - the authentication role to be parsed to an enum.
     * @return parsed authentication role as an enumeration.
     */
    public static AuthRole parseAuthRole(String authRole) {
        return Optional.ofNullable(authRole)
                .map(AuthRole::valueOf).orElse(AuthRole.None);
    }
}
