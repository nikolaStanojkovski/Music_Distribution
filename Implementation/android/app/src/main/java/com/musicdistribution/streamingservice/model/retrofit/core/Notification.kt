package com.musicdistribution.streamingservice.model.retrofit.core

import com.musicdistribution.streamingservice.model.enums.EntityType
import java.time.LocalDateTime

data class Notification(
    val listenerId: String,
    val publishingId: String,
    val isReceived: String,
    val type: EntityType,
    val publishedTime: LocalDateTime,
    val receivedTime: LocalDateTime,
    val listenerResponse: Listener
)