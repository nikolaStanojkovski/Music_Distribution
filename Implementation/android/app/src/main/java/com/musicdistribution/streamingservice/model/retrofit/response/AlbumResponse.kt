package com.musicdistribution.streamingservice.model.retrofit.response

import com.musicdistribution.streamingservice.model.retrofit.core.Album

data class AlbumResponse(
    val totalPages: Int,
    val totalElements: Long,
    val data: List<Album>
)