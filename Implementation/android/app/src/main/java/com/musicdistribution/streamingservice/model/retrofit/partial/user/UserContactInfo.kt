package com.musicdistribution.streamingservice.model.retrofit.partial.user

import com.musicdistribution.streamingservice.model.enums.EmailDomain

data class UserContactInfo(
    val email: EmailDomain,
    val telephoneNumber: String
)