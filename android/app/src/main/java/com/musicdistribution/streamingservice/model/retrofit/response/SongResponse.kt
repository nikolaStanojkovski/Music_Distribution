package com.musicdistribution.streamingservice.model.retrofit.response

import com.musicdistribution.streamingservice.model.retrofit.core.Song

data class SongResponse(
    val totalPages: Int,
    val totalElements: Long,
    val content: ArrayList<Song>
)