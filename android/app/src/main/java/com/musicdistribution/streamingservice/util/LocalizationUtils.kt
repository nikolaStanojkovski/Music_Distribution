package com.musicdistribution.streamingservice.util

import android.app.Activity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.musicdistribution.streamingservice.constant.MessageConstants
import java.util.*

@Suppress(MessageConstants.DEPRECATION)
object LocalizationUtils {

    fun getStringForGreeting(): String {
        val time = Calendar.getInstance().time

        when (time.hours) {
            in 0..5 -> {
                return MessageConstants.GOOD_NIGHT
            }
            in 6..11 -> {
                return MessageConstants.GOOD_MORNING
            }
            in 12..17 -> {
                return MessageConstants.GOOD_AFTERNOON
            }
            in 18..24 -> {
                return MessageConstants.GOOD_EVENING
            }
        }

        return MessageConstants.HELLO_THERE
    }

    fun getLocationProvider(context: Activity): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
}