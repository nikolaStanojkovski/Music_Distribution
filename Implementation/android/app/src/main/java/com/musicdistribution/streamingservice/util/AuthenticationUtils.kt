package com.musicdistribution.streamingservice.util

import com.musicdistribution.streamingservice.constants.AlphabetConstants
import com.musicdistribution.streamingservice.constants.ApiConstants
import com.musicdistribution.streamingservice.data.SessionService

object AuthenticationUtils {

    fun getAccessToken(): String {
        val accessToken = SessionService.read(ApiConstants.ACCESS_TOKEN)
        return if (accessToken != null && accessToken.isNotBlank())
            accessToken else AlphabetConstants.EMPTY_STRING
    }
}