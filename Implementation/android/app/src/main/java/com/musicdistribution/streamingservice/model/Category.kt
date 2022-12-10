package com.musicdistribution.streamingservice.model

data class Category(val id: Int, val title: String, var categoryItems: MutableList<CategoryItem>)