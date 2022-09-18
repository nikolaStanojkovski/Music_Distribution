package com.musicdistribution.albumdistribution.model.retrofit

import com.musicdistribution.albumdistribution.model.Genre

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