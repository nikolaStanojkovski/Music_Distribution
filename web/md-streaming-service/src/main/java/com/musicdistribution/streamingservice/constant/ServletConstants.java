package com.musicdistribution.streamingservice.constant;

/**
 * Helper class used for storing constants related to servlets.
 */
public final class ServletConstants {

    public final static String AUTH_HEADER = "Authorization";

    public final static String STATUS = "status";
    public final static String ERROR = "error";
    public final static String MESSAGE = "message";
    public final static String PATH = "path";
    public final static String RANGE = "Range";
    public final static String BYTES = "bytes";

    public final static String CONTENT_TYPE = "Content-Type";
    public final static String ACCEPT_RANGES = "Accept-Ranges";
    public final static String CONTENT_LENGTH = "Content-Length";
    public final static String CONTENT_RANGE = "Content-Range";
    public final static String AUDIO_CONTENT_TYPE = "audio/mpeg";

    /**
     * Private constructor which throws an exception because the class should not be instantiated.
     */
    private ServletConstants() {
        throw new UnsupportedOperationException(ExceptionConstants.UNSUPPORTED_CLASS_INSTANTIATION);
    }
}
