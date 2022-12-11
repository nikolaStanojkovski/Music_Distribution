package com.musicdistribution.streamingservice.model.retrofit.core

import com.musicdistribution.streamingservice.model.retrofit.partial.user.UserRegistrationInfo

data class Listener (
    val id: String,
    val email: String,
    val userRegistrationInfo: UserRegistrationInfo
)