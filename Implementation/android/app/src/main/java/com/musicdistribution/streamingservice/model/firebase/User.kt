package com.musicdistribution.streamingservice.model.firebase

import com.musicdistribution.streamingservice.model.Role

data class User(
    var name: String,
    var surname: String,
    val email: String,
    val role: Role,
    var noFollowers: Long,
    var noFollowing: Long
)