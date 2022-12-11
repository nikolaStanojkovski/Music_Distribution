package com.musicdistribution.streamingservice.data.api.enums

import retrofit2.Call
import retrofit2.http.GET

interface GenreServiceApi {

    @GET("/api/genres")
    fun getGenres(): Call<ArrayList<String>>
}