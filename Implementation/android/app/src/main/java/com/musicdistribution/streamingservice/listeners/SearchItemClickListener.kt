package com.musicdistribution.streamingservice.listeners

import com.musicdistribution.streamingservice.model.search.SearchItem

interface SearchItemClickListener {
    fun onClick(searchItem: SearchItem)
}