package com.musicdistribution.streamingservice.util

import com.musicdistribution.streamingservice.constant.AlphabetConstants

object StringUtils {

    fun generateTimeString(timeInSeconds: Int): String {
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds % 60
        val minutesString =
            if (minutes < 10) "${AlphabetConstants.ZERO}$minutes" else minutes.toString()
        val secondsString =
            if (seconds < 10) "${AlphabetConstants.ZERO}$seconds" else seconds.toString()
        return "$minutesString${AlphabetConstants.COLON}$secondsString"
    }

    fun isNumeric(string: String): Boolean {
        try {
            Integer.parseInt(string)
        } catch (e: NumberFormatException) {
            return false
        }

        return true
    }

    fun isDouble(string: String): Boolean {
        try {
            val parsedNumber = string.toDouble()
        } catch (e: NumberFormatException) {
            return false
        }

        return true
    }
}