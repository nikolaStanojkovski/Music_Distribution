package com.musicdistribution.streamingservice.model.retrofit

data class ListenerJwt(
    val listenerResponse: Listener,
    val jwtToken: String
)