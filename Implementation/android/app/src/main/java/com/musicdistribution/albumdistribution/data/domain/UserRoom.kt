package com.musicdistribution.albumdistribution.data.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserRoom(
    val name: String,
    val surname: String,
    val role: Role,
    val picture: String,
    val noFollowers: Long,
    val noFollowing: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
        set(value) { field = value }
}