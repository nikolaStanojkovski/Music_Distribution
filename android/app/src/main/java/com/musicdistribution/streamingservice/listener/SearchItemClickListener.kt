package com.musicdistribution.streamingservice.listener

import com.musicdistribution.streamingservice.model.search.SearchItem

interface SearchItemClickListener {
    fun onClick(searchItem: SearchItem)
}