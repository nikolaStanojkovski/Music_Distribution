package com.musicdistribution.streamingservice.constant;

/**
 * Helper class used for storing api path constants.
 */
public final class PathConstants {

    public static final String API = "/api";
    public static final String RESOURCE = "/resource";

    public static final String SUBSCRIPTION = "/subscription";
    public static final String TRANSACTION = "/transaction";
    public static final String CAPTURE = "/capture";
    public static final String CHECKOUT = "/checkout";
    public static final String SEARCH = "/search";
    public static final String PUBLISH = "/publish";
    public static final String RAISE_TIER = "/raise-tier";
    public static final String CREATE = "/create";
    public static final String ID = "/id";
    public static final String SEND = "/send";
    public static final String FAVOURITE = "/favourite";

    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String REGISTER_ARTIST = REGISTER + "/artist";
    public static final String REGISTER_LISTENER = REGISTER + "/listener";

    public static final String SONGS = "/songs";
    public static final String ALBUMS = "/albums";
    public static final String ARTISTS = "/artists";
    public static final String LISTENERS = "/listeners";
    public static final String NOTIFICATIONS = "/notifications";

    public static final String FORMATTED_ID = "/{id}";
    public static final String FORMATTED_ID_ENTITY_TYPE = "/{id}/{entityType}";

    public static final String API_EMAIL_DOMAINS = API + "/email-domains";
    public static final String API_GENRES = API + "/genres";
    public static final String API_PAYMENT = API + "/payment";
    public static final String API_TIERS = API + "/tiers";

    public static final String API_AUTH = API + "/auth";
    public static final String API_STREAM = API + RESOURCE + "/stream";
    public static final String API_SONGS = API + RESOURCE + SONGS;
    public static final String API_SONGS_SEARCH = API_SONGS + SEARCH;
    public static final String API_ALBUMS = API + RESOURCE + ALBUMS;
    public static final String API_ALBUMS_SEARCH = API_ALBUMS + SEARCH;
    public static final String API_ARTISTS = API + RESOURCE + ARTISTS;
    public static final String API_ARTISTS_SEARCH = API_ARTISTS + SEARCH;
    public static final String API_LISTENERS = API + RESOURCE + LISTENERS;
    public static final String API_LISTENERS_SEARCH = API_LISTENERS + SEARCH;
    public static final String API_FAVOURITE_ADD = FORMATTED_ID + FAVOURITE + "/add";
    public static final String API_FAVOURITE_REMOVE = FORMATTED_ID + FAVOURITE + "/remove";
    public static final String API_NOTIFICATIONS = API + RESOURCE + NOTIFICATIONS;
    public static final String API_NOTIFICATIONS_SEARCH = NOTIFICATIONS + SEARCH;
    public static final String API_PAYMENT_CAPTURE = API_PAYMENT + CAPTURE;

    public static final String API_AUDIO_STREAM = FORMATTED_ID + AlphabetConstants.DOT + FileConstants.MPEG_EXTENSION;
    public static final String API_SONG_COVER = SONGS + FORMATTED_ID
            + AlphabetConstants.DOT + FileConstants.PNG_EXTENSION;
    public static final String API_ALBUM_COVER = ALBUMS + FORMATTED_ID
            + AlphabetConstants.DOT + FileConstants.PNG_EXTENSION;
    public static final String API_ARTIST_COVER = ARTISTS + FORMATTED_ID
            + AlphabetConstants.DOT + FileConstants.PNG_EXTENSION;

    /**
     * Private constructor which throws an exception because the class should not be instantiated.
     */
    private PathConstants() {
        throw new UnsupportedOperationException(ExceptionConstants.UNSUPPORTED_CLASS_INSTANTIATION);
    }
}
