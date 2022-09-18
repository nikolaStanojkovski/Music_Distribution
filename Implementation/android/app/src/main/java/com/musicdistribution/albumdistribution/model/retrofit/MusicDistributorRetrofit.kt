package com.musicdistribution.albumdistribution.model.retrofit

data class MusicDistributorRetrofit(
    val id: String,
    val distributorInfo: String,
    val companyName: String,
    val distributorName: String,
    val totalEarned: Money,
    val noAlbumsPublished: Int,
)