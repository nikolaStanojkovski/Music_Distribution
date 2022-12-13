package com.musicdistribution.streamingservice.data

import com.musicdistribution.streamingservice.constants.MessageConstants
import com.musicdistribution.streamingservice.model.search.Category

object CategoryData {
    fun clearData() {
        mainData[0].categoryItems = mutableListOf()
        mainData[1].categoryItems = mutableListOf()
        mainData[2].categoryItems = mutableListOf()
        artistData[0].categoryItems = mutableListOf()
        artistData[1].categoryItems = mutableListOf()
    }

    val mainData: MutableList<Category> = mutableListOf(
        Category(1, MessageConstants.EXPLORE_SONGS, mutableListOf()),
        Category(2, MessageConstants.EXPLORE_ALBUMS, mutableListOf()),
        Category(3, MessageConstants.EXPLORE_ARTISTS, mutableListOf()),
    )

    val artistData: MutableList<Category> = mutableListOf(
        Category(4, MessageConstants.PUBLISHED_SONGS, mutableListOf()),
        Category(5, MessageConstants.PUBLISHED_ALBUMS, mutableListOf()),
    )
}