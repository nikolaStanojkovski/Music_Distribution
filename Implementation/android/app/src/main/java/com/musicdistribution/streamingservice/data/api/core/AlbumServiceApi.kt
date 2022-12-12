package com.musicdistribution.streamingservice.data.api.core

import com.musicdistribution.streamingservice.constants.ApiConstants
import com.musicdistribution.streamingservice.constants.EntityConstants
import com.musicdistribution.streamingservice.model.retrofit.core.Album
import com.musicdistribution.streamingservice.model.retrofit.core.Listener
import com.musicdistribution.streamingservice.model.retrofit.response.AlbumResponse
import com.musicdistribution.streamingservice.model.retrofit.response.NotificationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumServiceApi {

    @GET(ApiConstants.API_ALBUMS)
    fun findAll(): Call<AlbumResponse?>

    @GET(ApiConstants.API_ALBUMS_SEARCH)
    fun search(
        @Query(EntityConstants.SEARCH_PARAMS) searchParams: String,
        @Query(EntityConstants.SEARCH_TERM) searchTerm: String,
        @Query(EntityConstants.PAGE) page: Int,
        @Query(EntityConstants.SIZE) size: Int
    ): Call<AlbumResponse?>

    @GET(ApiConstants.API_ALBUMS_ID)
    fun findById(@Path(EntityConstants.ID) id: String): Call<Album?>
}