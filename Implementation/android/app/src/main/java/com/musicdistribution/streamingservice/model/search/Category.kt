package com.musicdistribution.streamingservice.model.search

data class Category(val id: Int, val title: String, var categoryItems: MutableList<CategoryItem>)