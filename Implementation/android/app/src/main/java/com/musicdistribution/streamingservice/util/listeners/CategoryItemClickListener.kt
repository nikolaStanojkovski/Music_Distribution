package com.musicdistribution.streamingservice.util.listeners

import com.musicdistribution.streamingservice.model.CategoryItem

interface CategoryItemClickListener {
    fun onClick(item: CategoryItem)
}