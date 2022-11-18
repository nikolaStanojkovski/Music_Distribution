package com.musicdistribution.storageservice.constant;

/**
 * Helper class used for storing constants for application properties.
 */
public final class PropertyConstants {

    public final static String MD_ENCRYPTION_KEY = "musicDistribution.app.encryptionKey";
    public final static String MD_ENCRYPTION_SCHEME = "musicDistribution.app.encryptionScheme";

    public final static String MD_JWT_SECRET = "${musicDistribution.app.jwtSecret}";
    public final static String MD_JWT_EXPIRATION_TIME = "${musicDistribution.app.jwtExpirationMs}";

    public final static String MD_SONG_FILES_LOCATION = "${file.songs.upload.location}";
    public final static String MD_SONG_COVER_FILES_LOCATION = "${file.cover-songs.upload.location}";
    public final static String MD_ALBUM_COVER_FILES_LOCATION = "${file.cover-albums.upload.location}";
    public final static String MD_ARTIST_COVER_FILES_LOCATION = "${file.profile-pictures.upload.location}";

    /**
     * Private constructor which throws an exception because the class should not be instantiated.
     */
    private PropertyConstants() {
        throw new UnsupportedOperationException(ExceptionConstants.UNSUPPORTED_CLASS_INSTANTIATION);
    }
}
