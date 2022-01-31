package com.musicdistribution.albumdistribution.data

import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.domain.Genre
import com.musicdistribution.albumdistribution.model.GenreItem

class GenreData {
    companion object {
        val data = mutableListOf<GenreItem>(
            GenreItem(Genre.Rock.name, R.drawable.rock),
            GenreItem(Genre.Metal.name, R.drawable.metal),
            GenreItem(Genre.Jazz.name, R.drawable.jazz),
            GenreItem(Genre.Funk.name, R.drawable.funk),
            GenreItem(Genre.Pop.name, R.drawable.pop),
            GenreItem(Genre.RnB.name, R.drawable.rnb),
        )
    }
}