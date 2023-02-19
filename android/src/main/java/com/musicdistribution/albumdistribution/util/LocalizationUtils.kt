package com.musicdistribution.streamingservice.util

import android.app.Activity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class LocalizationUtils {
    companion object {
        fun getStringForGreeting(): String {
            val time = Calendar.getInstance().time

            when (time.hours) {
                in 0..5 -> {
                    return "Good night"
                }
                in 6..11 -> {
                    return "Good morning"
                }
                in 12..17 -> {
                    return "Good afternoon"
                }
                in 18..24 -> {
                    return "Good evening"
                }
            }

            return "Hello there"
        }

        fun getStringForTime(): String {
            val time = Calendar.getInstance().time

            return "${if (time.hours >= 10) time.hours else "0" + time.hours}:${if (time.minutes >= 10) time.minutes else "0" + time.minutes}"
        }

        fun getLocationProvider(context: Activity): FusedLocationProviderClient {
            return LocationServices.getFusedLocationProviderClient(context)
        }
    }
}