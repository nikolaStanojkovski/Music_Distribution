package com.musicdistribution.streamingservice.constant;

/**
 * Helper class used for storing alphabet constants.
 */
public final class AlphabetConstants {

    public static final String NULL = "null";
    public static final String UNDERSCORE = "_";
    public static final String SCORE = "-";
    public static final String DOT = ".";
    public static final String COLON = ":";

    public static final char A_LOWERCASE = 'a';
    public static final char H_LOWERCASE = 'h';
    public static final char I_LOWERCASE = 'i';
    public static final char P_LOWERCASE = 'p';
    public static final char Q_LOWERCASE = 'q';
    public static final char Z_LOWERCASE = 'z';



    /**
     * Private constructor which throws an exception because the class should not be instantiated.
     */
    private AlphabetConstants() {
        throw new UnsupportedOperationException(ExceptionConstants.UNSUPPORTED_CLASS_INSTANTIATION);
    }
}
