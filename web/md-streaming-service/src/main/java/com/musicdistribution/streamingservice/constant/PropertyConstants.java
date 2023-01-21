package com.musicdistribution.streamingservice.constant;

/**
 * Helper class used for storing constants for application properties.
 */
public final class PropertyConstants {

    public final static String MD_ENCRYPTION_KEY = "music-distribution.app.encryption-key";
    public final static String MD_ENCRYPTION_SCHEME = "music-distribution.app.encryption-scheme";

    public final static String MD_JWT_SECRET = "${music-distribution.app.jwt-secret}";
    public final static String MD_JWT_EXPIRATION_TIME = "${music-distribution.app.jwt-expiration-ms}";

    public final static String MD_SONG_FILES_LOCATION = "${file.songs.upload.location}";
    public final static String MD_SONG_COVER_FILES_LOCATION = "${file.cover-songs.upload.location}";
    public final static String MD_ALBUM_COVER_FILES_LOCATION = "${file.cover-albums.upload.location}";
    public final static String MD_ARTIST_COVER_FILES_LOCATION = "${file.profile-pictures.upload.location}";

    public final static String MD_PAYPAL_CLIENT_ID = "${payment.paypal.app.client-id}";
    public final static String MD_PAYPAL_SECRET = "${payment.paypal.app.secret}";

    /**
     * Private constructor which throws an exception because the class should not be instantiated.
     */
    private PropertyConstants() {
        throw new UnsupportedOperationException(ExceptionConstants.UNSUPPORTED_CLASS_INSTANTIATION);
    }
}
