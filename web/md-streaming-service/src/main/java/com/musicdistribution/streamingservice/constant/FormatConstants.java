package com.musicdistribution.streamingservice.constant;

/**
 * Helper class used for file constants.
 */
public final class FormatConstants {

    public static final String DATE_FORMATTER = "yyyy-MM-dd, HH:mm";

    /**
     * Private constructor which throws an exception because the class should not be instantiated.
     */
    private FormatConstants() {
        throw new UnsupportedOperationException(ExceptionConstants.UNSUPPORTED_CLASS_INSTANTIATION);
    }
}
