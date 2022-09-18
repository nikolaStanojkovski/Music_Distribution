package com.musicdistribution.albumdistribution.model.retrofit

data class SongRetrofit(
    val id: String,
    val songName: String,
    val isASingle: Boolean,
    val songLength: SongLength?,
    val creator: ArtistRetrofit?,
    val album: AlbumRetrofit?
)