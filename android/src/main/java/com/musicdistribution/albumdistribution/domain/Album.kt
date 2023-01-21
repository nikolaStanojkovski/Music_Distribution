package com.musicdistribution.streamingservice.domain

data class Album(
    val id: String,
    val title: String,
    val length: Long,
    val genre: Genre,
    val producer: String,
    val arranger: String,
    val composer: String
)