package com.musicdistribution.streamingservice.data

import com.musicdistribution.streamingservice.constant.MessageConstants
import com.musicdistribution.streamingservice.model.search.Category
import com.musicdistribution.streamingservice.model.search.enums.CategoryItemType

object CategoryData {

    fun clearData() {
        mainData[0].categoryItems = mutableListOf()
        mainData[1].categoryItems = mutableListOf()
        mainData[2].categoryItems = mutableListOf()
        artistData[0].categoryItems = mutableListOf()
        artistData[1].categoryItems = mutableListOf()
    }

    val mainData: MutableList<Category> = mutableListOf(
        Category(1, MessageConstants.EXPLORE_SONGS, mutableListOf(), CategoryItemType.SONG),
        Category(2, MessageConstants.EXPLORE_ALBUMS, mutableListOf(), CategoryItemType.ALBUM),
        Category(3, MessageConstants.EXPLORE_ARTISTS, mutableListOf(), CategoryItemType.ARTIST),
    )

    val artistData: MutableList<Category> = mutableListOf(
        Category(
            4,
            MessageConstants.PUBLISHED_SONGS,
            mutableListOf(),
            CategoryItemType.PUBLISHED_SONG
        ),
        Category(
            5,
            MessageConstants.PUBLISHED_ALBUMS,
            mutableListOf(),
            CategoryItemType.PUBLISHED_ALBUM
        ),
    )
}