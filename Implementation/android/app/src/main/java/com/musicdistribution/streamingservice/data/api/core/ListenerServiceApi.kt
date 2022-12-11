package com.musicdistribution.streamingservice.data.api.core

import com.musicdistribution.streamingservice.constants.ApiConstants
import com.musicdistribution.streamingservice.constants.EntityConstants
import com.musicdistribution.streamingservice.model.enums.EntityType
import com.musicdistribution.streamingservice.model.retrofit.core.Listener
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ListenerServiceApi {

    @GET(ApiConstants.API_LISTENERS_ID)
    fun findById(@Path(EntityConstants.ID) id: String): Call<Listener?>

    @PUT(ApiConstants.API_LISTENERS_FAVOURITE)
    fun addToFavourite(
        @Path(EntityConstants.ID) id: String,
        @Query(EntityConstants.PUBLISHING_ID) publishingId: String,
        @Query(EntityConstants.ENTITY_TYPE) entityType: EntityType
    ): Call<Boolean?>
}