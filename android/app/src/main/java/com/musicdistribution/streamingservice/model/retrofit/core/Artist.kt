package com.musicdistribution.streamingservice.model.retrofit.core

import com.musicdistribution.streamingservice.model.retrofit.partial.user.UserContactInfo
import com.musicdistribution.streamingservice.model.retrofit.partial.user.UserPersonalInfo

class Artist(
    val id: String,
    val email: String,
    val userContactInfo: UserContactInfo,
    val userPersonalInfo: UserPersonalInfo
)