package com.musicdistribution.streamingservice.listener

import com.musicdistribution.streamingservice.model.enums.Genre

interface GenreItemClickListener {
    fun onClick(genre: Genre)
}