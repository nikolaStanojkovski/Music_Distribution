package com.musicdistribution.streamingservice.data.api.core

import com.musicdistribution.streamingservice.constants.ApiConstants
import com.musicdistribution.streamingservice.constants.EntityConstants
import com.musicdistribution.streamingservice.model.enums.EntityType
import com.musicdistribution.streamingservice.model.retrofit.core.Listener
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ListenerServiceApi {

    @GET(ApiConstants.API_LISTENERS_ID)
    fun findById(
        @Path(EntityConstants.ID) id: String,
        @Path(EntityConstants.ENTITY_TYPE) entityType: EntityType
    ): Call<Listener?>

    @POST(ApiConstants.API_LISTENERS_FAVOURITE_ADD)
    fun addToFavourite(
        @Path(EntityConstants.ID) id: String,
        @Query(EntityConstants.PUBLISHING_ID) publishingId: String,
        @Query(EntityConstants.ENTITY_TYPE) entityType: EntityType
    ): Call<Boolean?>

    @POST(ApiConstants.API_LISTENERS_FAVOURITE_REMOVE)
    fun removeFromFavourite(
        @Path(EntityConstants.ID) id: String,
        @Query(EntityConstants.PUBLISHING_ID) publishingId: String,
        @Query(EntityConstants.ENTITY_TYPE) entityType: EntityType
    ): Call<Boolean?>
}