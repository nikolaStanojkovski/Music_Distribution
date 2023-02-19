package com.musicdistribution.streamingservice.model.retrofit.core

import com.musicdistribution.streamingservice.model.enums.Genre
import com.musicdistribution.streamingservice.model.retrofit.partial.SongLength

data class Song(
    val id: String,
    val songName: String,
    val songGenre: Genre,
    val isASingle: Boolean,
    val isPublished: Boolean,

    val songLength: SongLength,
    val creator: Artist,
    val album: Album?
)