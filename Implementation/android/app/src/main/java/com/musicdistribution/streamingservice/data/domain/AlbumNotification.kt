package com.musicdistribution.streamingservice.data.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlbumNotification(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val creatorId: String,
    val creatorName: String,
    val albumId: String,
    val albumName: String
)