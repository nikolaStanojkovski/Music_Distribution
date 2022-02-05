package com.musicdistribution.albumdistribution.data

import com.musicdistribution.albumdistribution.model.Category

class CategoryData {
    companion object {
        val mainData: MutableList<Category> = mutableListOf(
            Category(1, "Explore Songs", mutableListOf()),
            Category(2, "Explore Albums", mutableListOf()),
            Category(3, "Explore Artists", mutableListOf()),
        )

        val artistData: MutableList<Category> = mutableListOf(
            Category(1, "Songs", mutableListOf()),
            Category(2, "Albums", mutableListOf()),
        )
    }
}