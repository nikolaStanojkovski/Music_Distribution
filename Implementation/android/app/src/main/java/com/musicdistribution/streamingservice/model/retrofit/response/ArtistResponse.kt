package com.musicdistribution.streamingservice.model.retrofit.response

import com.musicdistribution.streamingservice.model.retrofit.core.Artist

data class ArtistResponse(
    val totalPages: Int,
    val totalElements: Long,
    val data: List<Artist>
)