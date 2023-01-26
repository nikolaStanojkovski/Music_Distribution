package com.musicdistribution.albumdistribution.util.listeners

import com.musicdistribution.albumdistribution.model.SearchItem

interface SearchItemClickListener {
    fun onClick(searchItem: SearchItem)
}