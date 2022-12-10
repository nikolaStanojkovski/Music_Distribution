package com.musicdistribution.streamingservice.data

import com.musicdistribution.streamingservice.model.Category

object CategoryData {
    fun clearData() {
        mainData[0].categoryItems = mutableListOf()
        mainData[1].categoryItems = mutableListOf()
        mainData[2].categoryItems = mutableListOf()
        artistData[0].categoryItems = mutableListOf()
        artistData[1].categoryItems = mutableListOf()
    }

    val mainData: MutableList<Category> = mutableListOf(
        Category(1, "Explore Songs", mutableListOf()),
        Category(2, "Explore Albums", mutableListOf()),
        Category(3, "Explore Artists", mutableListOf()),
    )

    val artistData: MutableList<Category> = mutableListOf(
        Category(4, "Published Songs", mutableListOf()),
        Category(5, "Published Albums", mutableListOf()),
    )
}