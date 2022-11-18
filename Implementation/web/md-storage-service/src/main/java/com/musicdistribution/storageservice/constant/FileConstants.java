package com.musicdistribution.storageservice.constant;

/**
 * Helper class used for file constants.
 */
public final class FileConstants {

    public final static String PNG_EXTENSION = "png";
    public final static String MPEG_EXTENSION = "mp3";

    /**
     * Private constructor which throws an exception because the class should not be instantiated.
     */
    private FileConstants() {
        throw new UnsupportedOperationException(ExceptionConstants.UNSUPPORTED_CLASS_INSTANTIATION);
    }
}
