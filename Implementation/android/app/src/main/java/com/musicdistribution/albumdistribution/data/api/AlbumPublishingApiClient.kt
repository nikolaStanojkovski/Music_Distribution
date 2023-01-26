package com.musicdistribution.albumdistribution.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlbumPublishingApiClient {

    companion object {
        private var distributorApi: AlbumPublishingApi? = null

        fun getMusicDistributorApi(): AlbumPublishingApi? {
            if (distributorApi == null) {
                distributorApi = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8081/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(AlbumPublishingApi::class.java)
            }

            return distributorApi
        }
    }
}