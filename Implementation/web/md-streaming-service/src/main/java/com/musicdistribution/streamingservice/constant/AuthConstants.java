package com.musicdistribution.streamingservice.constant;

/**
 * Helper class used for storing authentication constants.
 */
public final class AuthConstants {

    public static final String AUTHENTICATION_ANT_MATCHER = PathConstants.API_AUTH + "/**";
    public static final String STREAM_ANT_MATCHER = PathConstants.API_STREAM + "/**";

    public static final String UNAUTHORIZED_MESSAGE = "Unauthorized";
    public static final String AUTH_ROLE = "Auth-Role";
    public static final String USERNAME_AUTH_ROLE_DELIMITER = AlphabetConstants.COLON;
    public static final String ADMIN_AUTHORITY = "ADMIN";

    public static final String JWT_TOKEN_PREFIX = "Bearer";
    public static final Integer JWT_TOKEN_LENGTH = 7;

    /**
     * Private constructor which throws an exception because the class should not be instantiated.
     */
    private AuthConstants() {
        throw new UnsupportedOperationException(ExceptionConstants.UNSUPPORTED_CLASS_INSTANTIATION);
    }
}
