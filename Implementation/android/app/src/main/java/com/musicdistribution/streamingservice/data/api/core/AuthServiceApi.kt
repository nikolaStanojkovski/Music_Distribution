package com.musicdistribution.streamingservice.data.api.core

import com.musicdistribution.streamingservice.model.retrofit.Listener
import com.musicdistribution.streamingservice.model.retrofit.ListenerJwt
import com.musicdistribution.streamingservice.model.retrofit.UserAuth
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthServiceApi {

    @POST("/api/auth/login")
    fun login(@Body authRequest: UserAuth): Call<ListenerJwt?>

    @POST("/api/auth/register/listener")
    fun register(@Body authRequest: UserAuth): Call<Listener?>
}