package com.musicdistribution.albumdistribution.model.retrofit

data class ArtistRetrofitAuth(
    val username: String,
    val emailDomain: EmailDomain,
    val telephoneNumber: String,

    val firstName: String,
    val lastName: String,
    val artName: String,

    val password: String
)