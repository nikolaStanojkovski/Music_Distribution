package com.musicdistribution.albumdistribution.data.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SongRoom(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val isASingle: Boolean,
    val length: Long,

    val userId: Long,
    val albumId: Long
)