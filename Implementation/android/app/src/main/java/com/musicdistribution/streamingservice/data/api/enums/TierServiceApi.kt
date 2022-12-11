package com.musicdistribution.streamingservice.data.api.enums

import retrofit2.Call
import retrofit2.http.GET

interface TierServiceApi {

    @GET("/api/tiers")
    fun getTiers(): Call<ArrayList<String>>
}