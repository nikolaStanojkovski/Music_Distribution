package com.musicbution.albumdistribution.data.api

import com.musicdistribution.albumdistribution.data.api.AlbumCatalogApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlbumCatalogApiClient {

    companion object {
        private var catalogApi: AlbumCatalogApi? = null

        fun getAlbumCatalogApi(): AlbumCatalogApi? {
            if (catalogApi == null) {
                catalogApi = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8082/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(AlbumCatalogApi::class.java)
            }

            return catalogApi
        }
    }
}