package com.musicdistribution.streamingservice.model.retrofit.partial

import com.musicdistribution.streamingservice.model.enums.Tier

data class PaymentInfo(
    val subscriptionFee: Money,
    val transactionFee: Money,
    val tier: Tier
)