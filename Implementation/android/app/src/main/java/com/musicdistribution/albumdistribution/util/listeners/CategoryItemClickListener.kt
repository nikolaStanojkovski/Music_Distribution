package com.musicdistribution.albumdistribution.util.listeners

import com.musicdistribution.albumdistribution.model.CategoryItem

interface CategoryItemClickListener {
    fun onClick(item: CategoryItem)
}