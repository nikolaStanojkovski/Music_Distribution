package com.musicdistribution.streamingservice.data

import com.musicdistribution.streamingservice.model.enums.Genre
import com.musicdistribution.streamingservice.model.search.GenreItem
import streamingservice.R

object GenreData {

    val data = mutableListOf(
        GenreItem(Genre.Rock, R.drawable.rock),
        GenreItem(Genre.Metal, R.drawable.metal),
        GenreItem(Genre.Jazz, R.drawable.jazz),
        GenreItem(Genre.Funk, R.drawable.funk),
        GenreItem(Genre.Pop, R.drawable.pop),
        GenreItem(Genre.RnB, R.drawable.rnb),
    )
}