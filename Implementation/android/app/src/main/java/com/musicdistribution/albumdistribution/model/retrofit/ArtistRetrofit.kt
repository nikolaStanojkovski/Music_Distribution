package com.musicdistribution.albumdistribution.model.retrofit

data class ArtistRetrofit(
    val id: String? = null,
    val email: String,
    val artistPersonalInfo: ArtistPersonalInfo,
    val password: String
)