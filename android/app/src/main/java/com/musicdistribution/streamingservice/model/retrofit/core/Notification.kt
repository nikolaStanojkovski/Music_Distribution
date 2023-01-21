package com.musicdistribution.streamingservice.model.retrofit.core

import com.musicdistribution.streamingservice.model.enums.EntityType

data class Notification(
    val listenerId: String,
    val publishingId: String,
    val isReceived: Boolean,
    val type: EntityType,

    val publishedTime: String,
    val receivedTime: String?,
    val creatorResponse: Artist,
    val listenerResponse: Listener
)