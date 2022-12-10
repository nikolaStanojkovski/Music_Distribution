package com.musicdistribution.streamingservice.data.api

import com.musicdistribution.streamingservice.model.retrofit.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AlbumCatalogApi {

    @GET("api/songs")
    fun getAllSongs(): Call<ArrayList<SongRetrofit>>

    @GET("api/albums")
    fun getAllAlbums(): Call<ArrayList<AlbumRetrofit>>


    @GET("api/albums/search/{searchTerm}")
    fun searchAlbums(@Path("searchTerm") searchTerm: String): Call<ArrayList<AlbumRetrofit>>

    @GET("api/artists/search/{searchTerm}")
    fun searchArtists(@Path("searchTerm") searchTerm: String): Call<ArrayList<ArtistRetrofit>>

    @GET("api/songs/search/{searchTerm}")
    fun searchSongs(@Path("searchTerm") searchTerm: String): Call<ArrayList<SongRetrofit>>


    @GET("api/albums/page")
    fun getAlbumsPage(): Call<ArrayList<AlbumRetrofit>>

    @GET("api/albums/genre/{genre}")
    fun getAlbumsGenre(@Path("genre") genre: String): Call<ArrayList<AlbumRetrofit>>

    @GET("api/artists/page")
    fun getArtistsPage(): Call<ArrayList<ArtistRetrofit>>

    @GET("api/songs/page")
    fun getSongsPage(): Call<ArrayList<SongRetrofit>>


    @POST("api/artists/login")
    fun loginArtist(@Body artist: ArtistRetrofitAuth): Call<ArtistRetrofit?>

    @POST("api/artists/register")
    fun registerArtist(@Body artist: ArtistRetrofitAuth): Call<ArtistRetrofit?>


    @GET("api/artists/{id}")
    fun getArtistById(@Path("id") id: String): Call<ArtistRetrofit?>

    @POST("api/artists/email")
    fun getArtistByEmail(@Body artist: ArtistRetrofitAuth): Call<ArtistRetrofit?>

    @GET("api/albums/artist/{artistId}")
    fun getArtistAlbums(@Path("artistId") artistId: String): Call<ArrayList<AlbumRetrofit>>

    @GET("api/songs/artist/{artistId}")
    fun getArtistSongs(@Path("artistId") artistId: String): Call<ArrayList<SongRetrofit>>


    @GET("api/albums/{id}")
    fun getAlbum(@Path("id") id: String): Call<AlbumRetrofit?>

    @GET("api/songs/album/{albumId}")
    fun getAlbumSongs(@Path("albumId") albumId: String): Call<ArrayList<SongRetrofit>>


    @GET("api/songs/{id}")
    fun getSong(@Path("id") id: String): Call<SongRetrofit?>


//    @POST("api/songs/publish")
//    fun publishSong(@Body album: SongRetrofitCreate): Call<SongRetrofit?>

    @GET("api/songs/unpublish/{id}")
    fun unPublishSong(@Path("id") id: String): Call<SongRetrofit?>

    @POST("api/albums/create")
    fun createAlbum(@Body album: AlbumRetrofitCreate): Call<AlbumRetrofit?>
}