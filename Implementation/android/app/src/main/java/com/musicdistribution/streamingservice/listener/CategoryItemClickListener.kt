package com.musicdistribution.streamingservice.listener

import com.musicdistribution.streamingservice.model.search.CategoryItem

interface CategoryItemClickListener {
    fun onClick(item: CategoryItem)
}