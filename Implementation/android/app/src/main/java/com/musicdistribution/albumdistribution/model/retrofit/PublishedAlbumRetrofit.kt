package com.musicdistribution.albumdistribution.model.retrofit

import com.musicdistribution.albumdistribution.data.domain.Tier
import java.time.Instant

data class PublishedAlbumRetrofit(
    val publishedAlbumId: String,
    val albumId: String,
    val albumName: String,
    val artistId: String,
    val artistInformation: String,
    val musicPublisherId: String,
    val musicPublisherInfo: String,
    val publishedOn: Instant,
    val albumTier: Tier
)