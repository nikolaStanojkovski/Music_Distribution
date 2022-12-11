package com.musicdistribution.streamingservice.listeners

import com.musicdistribution.streamingservice.model.enums.Genre

interface GenreItemClickListener {
    fun onClick(genre: Genre)
}