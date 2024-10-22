package com.musicdistribution.streamingservice.model.retrofit.core

data class Listener(
    val id: String,
    val email: String,

    val favouriteArtists: MutableList<Artist>?,
    val favouriteAlbums: MutableList<Album>?,
    val favouriteSongs: MutableList<Song>?
)