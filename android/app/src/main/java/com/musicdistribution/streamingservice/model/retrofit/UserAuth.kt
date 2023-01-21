package com.musicdistribution.streamingservice.model.retrofit

data class UserAuth(
    val username: String,
    val emailDomain: String,
    val password: String
)