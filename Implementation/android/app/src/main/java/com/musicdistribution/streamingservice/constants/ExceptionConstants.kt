package com.musicdistribution.streamingservice.constants

object ExceptionConstants {

    const val NO_INTERNET_CONNECTION = "No internet connection detected"
    const val INVALID_EMAIL_ADDRESS = "Invalid e-mail address format"
    const val UNSUPPORTED_EMAIL_DOMAIN = "Unsupported e-mail address domain"
    const val INVALID_PASSWORD = "Invalid password format"
    const val LOCATION_PERMISSIONS = "Error when trying to grant permissions for location"

    const val ARTISTS_FETCH_FAILED = "There was a problem when fetching the artists"
    const val ALBUMS_FETCH_FAILED = "There was a problem when fetching the albums"
    const val SONGS_FETCH_FAILED = "There was a problem when fetching the songs"

    const val SESSION_NOT_INITIALIZED = "The session service has not been initialized"
}