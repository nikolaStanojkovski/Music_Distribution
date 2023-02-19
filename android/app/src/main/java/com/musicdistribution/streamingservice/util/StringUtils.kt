package com.musicdistribution.streamingservice.util

import android.util.Log
import com.musicdistribution.streamingservice.constant.MessageConstants

object StringUtils {

    fun isDouble(string: String): Boolean {
        try {
            string.toDouble()
        } catch (e: NumberFormatException) {
            Log.e(MessageConstants.APPLICATION_ID, e.message, e)
            return false
        }

        return true
    }
}