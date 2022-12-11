package com.musicdistribution.streamingservice.data.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.musicdistribution.streamingservice.model.enums.Role

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val surname: String,
    val role: Role,
    val picture: String,
    val noFollowers: Long,
    val noFollowing: Long
)