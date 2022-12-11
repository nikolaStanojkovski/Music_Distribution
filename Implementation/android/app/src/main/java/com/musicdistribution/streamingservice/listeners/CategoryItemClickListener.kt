package com.musicdistribution.streamingservice.listeners

import com.musicdistribution.streamingservice.model.search.CategoryItem

interface CategoryItemClickListener {
    fun onClick(item: CategoryItem)
}