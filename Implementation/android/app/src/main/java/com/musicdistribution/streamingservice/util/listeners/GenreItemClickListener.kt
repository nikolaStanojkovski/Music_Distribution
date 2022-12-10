package com.musicdistribution.streamingservice.util.listeners

import com.musicdistribution.streamingservice.model.Genre

interface GenreItemClickListener {
    fun onClick(genre: Genre)
}