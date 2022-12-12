package com.musicdistribution.streamingservice.data.api.core

import com.musicdistribution.streamingservice.constants.ApiConstants
import com.musicdistribution.streamingservice.constants.EntityConstants
import com.musicdistribution.streamingservice.model.retrofit.core.Notification
import com.musicdistribution.streamingservice.model.retrofit.response.NotificationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NotificationServiceApi {

    @GET(ApiConstants.API_NOTIFICATIONS_SEARCH)
    fun search(
        @Query(EntityConstants.SEARCH_PARAMS) searchParams: String,
        @Query(EntityConstants.SEARCH_TERM) searchTerm: String,
        @Query(EntityConstants.PAGE) page: Int,
        @Query(EntityConstants.SIZE) size: Int
    ): Call<NotificationResponse?>

    @GET(ApiConstants.API_NOTIFICATIONS_RECEIVE)
    fun receive(
        @Query(EntityConstants.LISTENER_ID) listenerId: String,
        @Query(EntityConstants.PUBLISHING_ID) publishingId: String
    ): Call<Notification?>
}