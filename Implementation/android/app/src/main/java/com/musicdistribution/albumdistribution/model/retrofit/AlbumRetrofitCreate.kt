package com.musicdistribution.albumdistribution.model.retrofit

import com.musicdistribution.albumdistribution.model.Genre

data class AlbumRetrofitCreate(
    val albumName: String,
    val totalLength: Int? = null,
    val isPublished: Boolean,
    val genre: Genre? = null,
    val artistName: String,
    val producerName: String,
    val composerName: String,
    val creatorId: String
)