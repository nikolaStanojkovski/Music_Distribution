package com.musicdistribution.albumdistribution.domain

data class User(
    val id: String,
    val name: String,
    val surname: String,
    val role: Role,
    val picture: String,
    val noFollowers: Long,
    val noFollowing: Long
)