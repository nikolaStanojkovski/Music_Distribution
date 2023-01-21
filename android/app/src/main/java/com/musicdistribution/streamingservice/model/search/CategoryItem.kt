package com.musicdistribution.streamingservice.model.search

import com.musicdistribution.streamingservice.model.search.enums.CategoryItemType

data class CategoryItem(
    val itemId: String,
    val imageUrl: String,
    val itemType: CategoryItemType
)