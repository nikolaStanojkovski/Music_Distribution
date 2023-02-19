package com.musicdistribution.streamingservice.model.retrofit.core

import com.musicdistribution.streamingservice.model.enums.Genre
import com.musicdistribution.streamingservice.model.retrofit.partial.AlbumInfo
import com.musicdistribution.streamingservice.model.retrofit.partial.PaymentInfo
import com.musicdistribution.streamingservice.model.retrofit.partial.SongLength

data class Album(
    val id: String,
    val albumName: String,
    val genre: Genre,

    val paymentInfo: PaymentInfo,
    val totalLength: SongLength,
    val albumInfo: AlbumInfo,
    val creator: Artist,
)