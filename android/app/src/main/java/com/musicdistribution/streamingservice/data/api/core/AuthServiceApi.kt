package com.musicdistribution.streamingservice.data.api.core

import com.musicdistribution.streamingservice.constant.ApiConstants
import com.musicdistribution.streamingservice.model.retrofit.UserAuth
import com.musicdistribution.streamingservice.model.retrofit.core.Listener
import com.musicdistribution.streamingservice.model.retrofit.response.ListenerJwt
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthServiceApi {

    @POST(ApiConstants.API_LOGIN)
    fun login(@Body authRequest: UserAuth): Call<ListenerJwt?>

    @POST(ApiConstants.API_REGISTER)
    fun register(@Body authRequest: UserAuth): Call<Listener?>
}