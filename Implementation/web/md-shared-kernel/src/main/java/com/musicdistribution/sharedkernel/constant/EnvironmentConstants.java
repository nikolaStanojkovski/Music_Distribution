package com.musicdistribution.sharedkernel.constant;

/**
 * Helper class used for storing environment constants.
 */
public final class EnvironmentConstants {

    public static final String CROSS_ORIGIN_DOMAIN = "http://localhost:3000";

    /**
     * Private constructor which throws an exception because the class should not be instantiated.
     */
    private EnvironmentConstants() {
        throw new UnsupportedOperationException();
    }
}
