package com.musicdistribution.albumdistribution.model.firebase

import com.musicdistribution.albumdistribution.data.domain.Role

data class User(
    var name: String,
    var surname: String,
    val email: String,
    val role: Role,
    val noFollowers: Long,
    val noFollowing: Long
)