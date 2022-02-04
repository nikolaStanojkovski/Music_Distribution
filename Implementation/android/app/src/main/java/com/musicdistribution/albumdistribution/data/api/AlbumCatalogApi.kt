package com.musicdistribution.albumdistribution.data.api

import com.musicdistribution.albumdistribution.model.retrofit.AlbumRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.ArtistRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.ArtistRetrofitAuth
import com.musicdistribution.albumdistribution.model.retrofit.SongRetrofit
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlbumCatalogApi {
    @GET("api/albums")
    fun getAllAlbums(): Call<ArrayList<AlbumRetrofit>>

    @GET("api/artists")
    fun getAllArtists(): Call<ArrayList<ArtistRetrofit>>

    @GET("api/albums")
    fun getAllSongs(): Call<ArrayList<SongRetrofit>>

    @GET("api/albums/page")
    fun getAlbumsPage(): Call<ArrayList<AlbumRetrofit>>

    @GET("api/artists/page")
    fun getArtistsPage(): Call<ArrayList<ArtistRetrofit>>

    @GET("api/albums/page")
    fun getSongsPage(): Call<ArrayList<SongRetrofit>>


    @POST("api/artists/login")
    fun loginArtist(@Body artist: ArtistRetrofitAuth): Call<ArtistRetrofit>

    @POST("api/artists/register")
    fun registerArtist(@Body artist: ArtistRetrofitAuth): Call<ArtistRetrofit>
}