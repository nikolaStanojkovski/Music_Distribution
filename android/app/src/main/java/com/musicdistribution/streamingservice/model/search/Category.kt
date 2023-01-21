package com.musicdistribution.streamingservice.model.search

import com.musicdistribution.streamingservice.model.search.enums.CategoryItemType

data class Category(
    val id: Int,
    val title: String,
    var categoryItems: MutableList<CategoryItem>,
    val categoryItemType: CategoryItemType
)