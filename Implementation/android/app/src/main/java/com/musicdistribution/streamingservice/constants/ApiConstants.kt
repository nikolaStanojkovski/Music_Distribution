package com.musicdistribution.streamingservice.constants

object ApiConstants {
    const val AUTH_ROLE = "Auth-Role"
    const val ACCESS_TOKEN = "accessToken"
    const val LISTENER_ROLE = "LISTENER"

    const val BASE_URL = "http://10.0.2.2:8082"

    private const val API = "/api"
    private const val AUTH = "/auth"
    private const val ALBUMS = "/albums"
    private const val ARTISTS = "/artists"
    private const val SONGS = "/songs"
    private const val NOTIFICATIONS = "/notifications"
    private const val LISTENERS = "/listeners"
    private const val SEARCH = "/search"
    private const val FORMATTED_ID = "/{id}"

    const val API_LOGIN = "$API$AUTH/login"
    const val API_REGISTER = "$API$AUTH/register/listener"

    const val API_ALBUMS_SEARCH = "$API$ALBUMS$SEARCH"
    const val API_ALBUMS_ID = "$API$ALBUMS$FORMATTED_ID"

    const val API_ARTISTS_SEARCH = "$API$ARTISTS$SEARCH"
    const val API_ARTISTS_ID = "$API$ARTISTS$FORMATTED_ID"

    const val API_SONGS_SEARCH = "$API$SONGS$SEARCH"
    const val API_SONGS_ID = "$API$SONGS$FORMATTED_ID"

    const val API_LISTENERS_ID = "$API$LISTENERS$FORMATTED_ID"
    const val API_LISTENERS_FAVOURITE = "$API$LISTENERS$FORMATTED_ID/favourite"

    const val API_NOTIFICATIONS_SEARCH = "$API$NOTIFICATIONS$SEARCH"
    const val API_NOTIFICATIONS_RECEIVE = "$API$NOTIFICATIONS/send"
}