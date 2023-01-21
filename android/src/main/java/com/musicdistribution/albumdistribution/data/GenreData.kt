package com.musicdistribution.streamingservice.data

import com.musicdistribution.streamingservice.R
import com.musicdistribution.streamingservice.domain.Genre
import com.musicdistribution.streamingservice.model.GenreItem

class GenreData {
    companion object {
        val data = mutableListOf<GenreItem>(
            GenreItem(Genre.Rock.name, R.drawable.rock),
            GenreItem(Genre.Blues.name, R.drawable.blues),
            GenreItem(Genre.Indie.name, R.drawable.indie),
            GenreItem(Genre.Metal.name, R.drawable.metal),
            GenreItem(Genre.Jazz.name, R.drawable.jazz),
            GenreItem(Genre.Soul.name, R.drawable.soul),
            GenreItem(Genre.Funk.name, R.drawable.funk),
            GenreItem(Genre.Pop.name, R.drawable.pop),
            GenreItem(Genre.Industrial.name, R.drawable.industrial),
        )
    }
}