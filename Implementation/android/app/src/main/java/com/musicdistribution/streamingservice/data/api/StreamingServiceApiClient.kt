package com.musicdistribution.streamingservice.data.api

import com.musicdistribution.streamingservice.constants.ApiConstants
import com.musicdistribution.streamingservice.data.api.core.AuthServiceApi
import com.musicdistribution.streamingservice.data.api.core.ListenerServiceApi
import com.musicdistribution.streamingservice.data.api.core.NotificationServiceApi
import com.musicdistribution.streamingservice.data.api.enums.EmailDomainServiceApi
import com.musicdistribution.streamingservice.data.api.enums.GenreServiceApi
import com.musicdistribution.streamingservice.data.api.enums.TierServiceApi
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class StreamingServiceApiClient {

    companion object {

        private var emailDomainServiceApi: EmailDomainServiceApi? = null
        private var genreServiceApi: GenreServiceApi? = null
        private var tierServiceApi: TierServiceApi? = null

        private var authServiceApi: AuthServiceApi? = null
        private var listenerServiceApi: ListenerServiceApi? = null
        private var notificationServiceApi: NotificationServiceApi? = null

        fun getEmailDomainService(): EmailDomainServiceApi {
            if (emailDomainServiceApi == null) {
                emailDomainServiceApi = buildServiceApi(EmailDomainServiceApi::class.java)
            }
            return emailDomainServiceApi!!
        }

        fun getGenreServiceApi(): GenreServiceApi {
            if (genreServiceApi == null) {
                genreServiceApi = buildServiceApi(GenreServiceApi::class.java)
            }
            return genreServiceApi!!
        }

        fun getTierServiceApi(): TierServiceApi {
            if (tierServiceApi == null) {
                tierServiceApi = buildServiceApi(TierServiceApi::class.java)
            }
            return tierServiceApi!!
        }

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

        private fun <T> buildServiceApi(streamingServiceClass: Class<T>): T {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                val request: Request =
                    chain.request().newBuilder()
                        .addHeader(ApiConstants.AUTH_ROLE, ApiConstants.LISTENER_ROLE)
                        .build()
                chain.proceed(request)
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