package com.musicdistribution.streamingservice.model.retrofit.core

import com.musicdistribution.streamingservice.model.retrofit.partial.user.UserRegistrationInfo

data class Listener (
    val id: String,
    val email: String,
    val userRegistrationInfo: UserRegistrationInfo,

    val favouriteArtists: MutableList<Artist>,
    val favouriteAlbums: MutableList<Album>,
    val favouriteSongs: MutableList<Song>
)