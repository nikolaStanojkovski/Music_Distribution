package com.musicdistribution.albumdistribution.model.firebase

import com.musicdistribution.albumdistribution.model.Role

data class User(
    var name: String,
    var surname: String,
    val email: String,
    val role: Role,
    var noFollowers: Long,
    var noFollowing: Long
)