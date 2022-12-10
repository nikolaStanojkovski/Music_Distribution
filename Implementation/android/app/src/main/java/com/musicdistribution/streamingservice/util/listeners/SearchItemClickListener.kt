package com.musicdistribution.streamingservice.util.listeners

import com.musicdistribution.streamingservice.model.SearchItem

interface SearchItemClickListener {
    fun onClick(searchItem: SearchItem)
}