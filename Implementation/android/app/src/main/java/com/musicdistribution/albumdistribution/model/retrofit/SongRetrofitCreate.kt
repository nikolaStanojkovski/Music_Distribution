package com.musicdistribution.albumdistribution.model.retrofit

data class SongRetrofitCreate(
    val songName: String,
    val isASingle: Boolean,
    val lengthInSeconds: Int,
    val creatorId: String,
    val albumId: String
)