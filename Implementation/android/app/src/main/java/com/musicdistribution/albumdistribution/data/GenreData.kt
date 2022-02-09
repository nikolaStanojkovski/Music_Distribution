package com.musicdistribution.albumdistribution.data

import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.model.Genre
import com.musicdistribution.albumdistribution.model.GenreItem

class GenreData {
    companion object {
        val data = mutableListOf(
            GenreItem(Genre.Rock, R.drawable.rock),
            GenreItem(Genre.Metal, R.drawable.metal),
            GenreItem(Genre.Jazz, R.drawable.jazz),
            GenreItem(Genre.Funk, R.drawable.funk),
            GenreItem(Genre.Pop, R.drawable.pop),
            GenreItem(Genre.RnB, R.drawable.rnb),
        )
    }
}