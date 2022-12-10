package com.musicdistribution.streamingservice.data.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SongNotification(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val creatorId: String,
    val creatorName: String,
    val songId: String,
    val songName: String
)