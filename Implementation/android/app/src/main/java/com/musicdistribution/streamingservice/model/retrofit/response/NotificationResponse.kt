package com.musicdistribution.streamingservice.model.retrofit.response

import com.musicdistribution.streamingservice.model.retrofit.core.Notification

data class NotificationResponse(
    val totalPages: Int,
    val totalElements: Long,
    val data: List<Notification>
)