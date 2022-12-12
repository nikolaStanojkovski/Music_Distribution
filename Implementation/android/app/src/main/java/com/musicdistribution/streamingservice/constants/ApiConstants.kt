package com.musicdistribution.streamingservice.constants

object ApiConstants {
    const val AUTH_ROLE = "Auth-Role"
    const val AUTHORIZATION = "Authorization"
    const val ACCESS_TOKEN = "accessToken"
    const val LISTENER_ROLE = "LISTENER"

    const val BASE_URL = "http://10.0.2.2:8082"

    private const val API = "/api"
    private const val RESOURCE = "/resource"
    private const val AUTH = "/auth"
    private const val STREAM = "/stream"
    private const val ALBUMS = "/albums"
    private const val ARTISTS = "/artists"
    private const val SONGS = "/songs"
    private const val NOTIFICATIONS = "/notifications"
    private const val LISTENERS = "/listeners"
    private const val SEARCH = "/search"
    private const val FORMATTED_ID = "/{id}"

    const val API_LOGIN = "$API$AUTH/login"
    const val API_REGISTER = "$API$AUTH/register/listener"

    const val API_STREAM_ARTISTS = "$API$RESOURCE$STREAM$ARTISTS"
    const val API_STREAM_SONGS = "$API$RESOURCE$STREAM$SONGS"
    const val API_STREAM_ALBUMS = "$API$RESOURCE$STREAM$ALBUMS"

    const val API_ALBUMS = "$API$RESOURCE$ALBUMS"
    const val API_ALBUMS_SEARCH = "$API$RESOURCE$ALBUMS$SEARCH"
    const val API_ALBUMS_ID = "$API$RESOURCE$ALBUMS$FORMATTED_ID"

    const val API_ARTISTS = "$API$RESOURCE$ARTISTS"
    const val API_ARTISTS_SEARCH = "$API$RESOURCE$ARTISTS$SEARCH"
    const val API_ARTISTS_ID = "$API$RESOURCE$ARTISTS$FORMATTED_ID"

    const val API_SONGS = "$API$RESOURCE$SONGS"
    const val API_SONGS_SEARCH = "$API$RESOURCE$SONGS$SEARCH"
    const val API_SONGS_ID = "$API$RESOURCE$SONGS$FORMATTED_ID"

    const val API_LISTENERS_ID = "$API$RESOURCE$LISTENERS$FORMATTED_ID"
    const val API_LISTENERS_FAVOURITE = "$API$RESOURCE$LISTENERS$FORMATTED_ID/favourite"

    const val API_NOTIFICATIONS_SEARCH = "$API$RESOURCE$NOTIFICATIONS$SEARCH"
    const val API_NOTIFICATIONS_RECEIVE = "$API$RESOURCE$NOTIFICATIONS/send"
}