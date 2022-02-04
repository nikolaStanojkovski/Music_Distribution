package com.musicdistribution.albumdistribution.model.retrofit

data class ArtistRetrofit(
    val id: String? = null,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String
)