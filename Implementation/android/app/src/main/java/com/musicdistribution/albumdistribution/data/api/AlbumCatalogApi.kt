package com.musicdistribution.albumdistribution.data.api

import com.musicdistribution.albumdistribution.model.retrofit.AlbumRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.ArtistRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.SongRetrofit
import retrofit2.Call
import retrofit2.http.GET

interface AlbumCatalogApi {
    @GET("api/albums")
    fun getAllAlbums(): Call<ArrayList<AlbumRetrofit>>

    @GET("api/artists")
    fun getAllArtists(): Call<ArrayList<ArtistRetrofit>>

    @GET("api/albums")
    fun getAllSongs(): Call<ArrayList<SongRetrofit>>
}