package com.musicdistribution.streamingservice.model.retrofit.response

import com.musicdistribution.streamingservice.model.retrofit.core.Listener

data class ListenerJwt(
    val listenerResponse: Listener,
    val jwtToken: String
)