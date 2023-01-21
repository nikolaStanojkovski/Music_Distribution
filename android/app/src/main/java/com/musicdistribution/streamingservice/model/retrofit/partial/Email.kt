package com.musicdistribution.streamingservice.model.retrofit.partial

import com.musicdistribution.streamingservice.model.enums.EmailDomain

data class Email(
    val domainUsername: String,
    val domainName: EmailDomain,
    val fullAddress: String
)