package com.musicdistribution.albumdistribution.util.listeners

import com.musicdistribution.albumdistribution.data.domain.Genre

interface GenreItemClickListener {
    fun onClick(genre: Genre)
}