package com.musicdistribution.albumdistribution.model.retrofit

import com.musicdistribution.albumdistribution.data.domain.Tier
import java.time.Instant

data class PublishedAlbumRetrofitCreate(
    val publishedAlbumId: String? = null,

    val albumId: String,
    val albumName: String,

    val artistId: String,
    val artistInformation: String,

    val musicPublisherId: String,
    val musicPublisherInfo: String,

    val publishedOn: Instant? = null,
    val albumTier: String,
    val totalCost: Money? = null,

    val subscriptionFee: Double,
    val transactionFee: Double
)