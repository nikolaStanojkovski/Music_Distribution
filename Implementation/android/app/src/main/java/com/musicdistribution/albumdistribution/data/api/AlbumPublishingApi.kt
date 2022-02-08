package com.musicdistribution.albumdistribution.data.api

import com.musicdistribution.albumdistribution.model.retrofit.MusicDistributorRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.PublishedAlbumRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.PublishedAlbumRetrofitCreate
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AlbumPublishingApi {
    @GET("api/distributors")
    fun getAllDistributors(): Call<ArrayList<MusicDistributorRetrofit>>

    @GET("api/publishedAlbums/artist/{artistId}")
    fun getAllPublishedAlbumsByArtist(@Path("artistId") artistId: String): Call<ArrayList<PublishedAlbumRetrofit>>


    @POST("api/distributors/publish")
    fun publishAlbum(@Body publishedAlbumRequest: PublishedAlbumRetrofitCreate): Call<Void>

    @GET("api/distributors/unPublish/{albumId}")
    fun unPublishAlbum(@Path("albumId") albumId: String): Call<Void>

    @POST("api/distributors/raiseAlbumTier")
    fun raiseAlbumTier(@Body publishedAlbumRequest: PublishedAlbumRetrofitCreate): Call<Void>
}