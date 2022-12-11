package com.musicdistribution.streamingservice.data.api.enums

import retrofit2.Call
import retrofit2.http.GET

interface EmailDomainServiceApi {

    @GET("/api/email-domains")
    fun getEmailDomains(): Call<ArrayList<String>>
}