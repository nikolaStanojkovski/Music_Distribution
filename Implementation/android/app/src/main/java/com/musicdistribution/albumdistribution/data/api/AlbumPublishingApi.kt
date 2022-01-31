package com.musicdistribution.albumdistribution.data.api

import com.musicdistribution.albumdistribution.model.retrofit.MusicDistributorRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.PublishedAlbumRetrofit
import retrofit2.Call
import retrofit2.http.GET

interface AlbumPublishingApi {
    @GET("api/distributors")
    fun getAllDistributors(): Call<ArrayList<MusicDistributorRetrofit>>

    @GET("api/publishedAlbums")
    fun getAllPublishedAlbums(): Call<ArrayList<PublishedAlbumRetrofit>>
}