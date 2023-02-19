package com.musicdistribution.streamingservice.model.search

import com.musicdistribution.streamingservice.model.search.enums.CategoryItemType

data class SearchItem(
    val searchItemId: String,
    val searchItemTitle: String,
    val searchItemInfo: String,
    val searchItemType: CategoryItemType,
    val searchItemImage: String
)