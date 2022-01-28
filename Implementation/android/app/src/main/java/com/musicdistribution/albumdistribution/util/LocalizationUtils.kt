package com.musicdistribution.albumdistribution.util

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

            return "${time.hours}:${time.minutes}"
        }

        fun getLocationProvider(context: Activity): FusedLocationProviderClient {
            return LocationServices.getFusedLocationProviderClient(context)
        }
    }
}