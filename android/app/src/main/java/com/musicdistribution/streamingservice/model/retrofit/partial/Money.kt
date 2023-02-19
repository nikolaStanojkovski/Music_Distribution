package com.musicdistribution.streamingservice.model.retrofit.partial

import com.musicdistribution.streamingservice.model.enums.Currency

data class Money(
    val currency: Currency,
    val amount: Double
)