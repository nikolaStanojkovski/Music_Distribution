package com.musicdistribution.streamingservice.model.retrofit.partial.user

import com.musicdistribution.streamingservice.model.retrofit.partial.Email

data class UserContactInfo(
    val email: Email,
    val telephoneNumber: String
)