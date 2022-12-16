package com.musicdistribution.streamingservice.data.api

import com.musicdistribution.streamingservice.constants.ApiConstants
import com.musicdistribution.streamingservice.data.api.core.*
import com.musicdistribution.streamingservice.util.AuthenticationUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class StreamingServiceApiClient {

    companion object {

        private var authServiceApi: AuthServiceApi? = null
        private var listenerServiceApi: ListenerServiceApi? = null
        private var notificationServiceApi: NotificationServiceApi? = null

        private var artistServiceApi: ArtistServiceApi? = null
        private var albumServiceApi: AlbumServiceApi? = null
        private var songServiceApi: SongServiceApi? = null

        fun getAuthServiceApi(): AuthServiceApi {
            if (authServiceApi == null) {
                authServiceApi = buildServiceApi(AuthServiceApi::class.java)
            }
            return authServiceApi!!
        }

        fun getListenerServiceApi(): ListenerServiceApi {
            if (listenerServiceApi == null) {
                listenerServiceApi = buildServiceApi(ListenerServiceApi::class.java)
            }
            return listenerServiceApi!!
        }

        fun getNotificationServiceApi(): NotificationServiceApi {
            if (notificationServiceApi == null) {
                notificationServiceApi = buildServiceApi(NotificationServiceApi::class.java)
            }
            return notificationServiceApi!!
        }

        fun getAlbumServiceApi(): AlbumServiceApi {
            if (albumServiceApi == null) {
                albumServiceApi = buildServiceApi(AlbumServiceApi::class.java)
            }
            return albumServiceApi!!
        }

        fun getArtistServiceApi(): ArtistServiceApi {
            if (artistServiceApi == null) {
                artistServiceApi = buildServiceApi(ArtistServiceApi::class.java)
            }
            return artistServiceApi!!
        }

        fun getSongServiceApi(): SongServiceApi {
            if (songServiceApi == null) {
                songServiceApi = buildServiceApi(SongServiceApi::class.java)
            }
            return songServiceApi!!
        }

        private fun <T> buildServiceApi(streamingServiceClass: Class<T>): T {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .addHeader(ApiConstants.AUTH_ROLE, ApiConstants.LISTENER_ROLE)
                        .addHeader(
                            ApiConstants.AUTHORIZATION,
                            "Bearer ${AuthenticationUtils.getAccessToken()}"
                        )
                        .build()
                )
            }
            return Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                .create(streamingServiceClass)
        }


    }
}