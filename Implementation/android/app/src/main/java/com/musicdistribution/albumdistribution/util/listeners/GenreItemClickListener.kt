package com.musicdistribution.albumdistribution.util.listeners

import com.musicdistribution.albumdistribution.model.Genre

interface GenreItemClickListener {
    fun onClick(genre: Genre)
}