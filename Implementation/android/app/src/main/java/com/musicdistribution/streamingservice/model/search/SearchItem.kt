package com.musicdistribution.streamingservice.model.search

import com.musicdistribution.streamingservice.model.search.CategoryItemType

data class SearchItem(
    val searchItemId: String,
    val searchItemTitle: String,
    val searchItemInfo: String,
    val searchItemType: CategoryItemType,
    val searchItemImage: String
)