package com.musicdistribution.streamingservice.model.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotificationRoom(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val listenerId: String,
    val creatorId: String,
    val publishingId: String
)