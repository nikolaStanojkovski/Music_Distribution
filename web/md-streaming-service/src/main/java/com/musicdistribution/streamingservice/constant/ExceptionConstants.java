package com.musicdistribution.streamingservice.constant;

/**
 * Helper class used for storing exception constants.
 */
public final class ExceptionConstants {

    public final static String DEPRECATION_MESSAGE = "deprecation";
    public final static String UNSUPPORTED_CLASS_INSTANTIATION = "The class is not meant to be instantiated.";

    public final static String ARTIST_USERNAME_NOT_FOUND = "Artist with username %s was not found.";
    public final static String LISTENER_USERNAME_NOT_FOUND = "Listener with username %s was not found.";
    public final static String USER_NOT_FOUND = "User with username %s was not found.";

    public final static String STRING_ENCRYPTION_FAILURE = "Encryption of the string %s has failed.";
    public final static String STRING_DECRYPTION_FAILURE = "Decryption of the string %s has failed.";

    public final static String FILE_NOT_FOUND = "Could not find the file with the specified file name.";
    public final static String FILE_LOADING_FAILURE = "Could not load the file with the specified file name.";
    public final static String DUPLICATE_FILE_FAILURE = "The file with the specified name %s already exists.";
    public final static String MULTIPART_FILE_FAILURE = "Could not save a new multipart file.";
    public final static String FILE_BYTES_LOADING_FAILURE = "The bytes from the file %s with start index %s and end index %s failed to be loaded.";
    public final static String FILES_FOLDER_FAILURE = "Could not create the files folder.";
    public final static String SONG_NOT_SAVED = "Could not save the song with id %s.";

    /**
     * Private constructor which throws an exception because the class should not be instantiated.
     */
    private ExceptionConstants() {
        throw new UnsupportedOperationException(UNSUPPORTED_CLASS_INSTANTIATION);
    }
}
