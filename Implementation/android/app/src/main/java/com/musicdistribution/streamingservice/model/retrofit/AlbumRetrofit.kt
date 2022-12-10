package com.musicdistribution.streamingservice.model.retrofit

import com.musicdistribution.streamingservice.model.Genre

data class AlbumRetrofit(
    val id: String,
    val albumName: String,
    val totalLength: SongLength,
    val isPublished: Boolean,
    val genre: Genre,
    val artistId: String,
    val artistName: String,
    val creator: ArtistRetrofit
)