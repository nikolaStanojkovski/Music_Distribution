package com.musicdistribution.streamingservice.constant

object ExceptionConstants {

    const val NO_INTERNET_CONNECTION = "No internet connection detected"
    const val INVALID_EMAIL_ADDRESS = "Invalid e-mail address format"
    const val UNSUPPORTED_EMAIL_DOMAIN = "Unsupported e-mail address domain"
    const val INVALID_PASSWORD = "Invalid password format"
    const val LOCATION_PERMISSIONS = "Error when trying to grant permissions for location"

    const val ARTISTS_FETCH_FAILED = "There was a problem when fetching the artists"
    const val ALBUMS_FETCH_FAILED = "There was a problem when fetching the albums"
    const val SONGS_FETCH_FAILED = "There was a problem when fetching the songs"
    const val NOTIFICATIONS_FETCH_FAILED = "There was a problem when fetching the notifications"
    const val SONG_FETCH_FAILED = "There was a problem when fetching the song with id "
    const val ALBUM_FETCH_FAILED = "There was a problem when fetching the album with id "
    const val ARTIST_FETCH_FAILED = "There was a problem when fetching the artist with id "
    const val NOTIFICATION_FETCH_FAILED = "There was a problem when fetching the notification with id "
    const val LISTENER_DATA_FETCH_FAILED = "There was a problem when fetching the listener data with id "
    const val ARTIST_PICTURE_FETCH_FAILED = "There was a problem when fetching the the artist image"
    const val ALBUM_PICTURE_FETCH_FAILED = "There was a problem when fetching the the album cover image"
    const val SONG_PICTURE_FETCH_FAILED = "There was a problem when fetching the the song cover image"
    const val FAVOURITE_ADD_FAILED = "There was a problem when liking the item with id "
    const val FAVOURITE_REMOVE_FAILED = "There was a problem when un-liking the item with id "

    const val GENRE_DATA_NOT_LOADED = "There was a problem when filling the genre filtering data"
    const val FRAGMENT_DATA_NOT_LOADED = "There was a problem when loading the screen data"
    const val SESSION_NOT_INITIALIZED = "The session service has not been initialized"
    const val UNKNOWN_VIEW_MODEL = "Unknown ViewModel class"
}