package com.musicdistribution.albumdistribution.model.firebase

import com.musicdistribution.albumdistribution.data.domain.Role

data class User(
    val name: String,
    val surname: String,
    val email: String,
    val role: Role,
    val picture: String,
    val noFollowers: Long,
    val noFollowing: Long
)