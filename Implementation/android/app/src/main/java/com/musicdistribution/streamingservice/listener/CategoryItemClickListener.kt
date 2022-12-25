package com.musicdistribution.streamingservice.listener

import com.musicdistribution.streamingservice.model.search.CategoryItem
import com.musicdistribution.streamingservice.model.search.CategoryItemType

interface CategoryItemClickListener {
    fun onItemClick(item: CategoryItem)
    fun onShowMoreClick(itemType: CategoryItemType)
}