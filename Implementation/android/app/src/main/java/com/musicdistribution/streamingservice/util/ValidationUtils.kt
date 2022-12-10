package com.musicdistribution.streamingservice.util

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt

class ValidationUtils {
    companion object {
        fun validateUsername(email: String, context: Context): Boolean {
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                val firstLastName = generateFirstLastName(email)
                if (firstLastName.size == 2 && firstLastName[0].isNotBlank() && firstLastName[1].isNotBlank()) {
                    Toast.makeText(context, "Invalid e-mail address", Toast.LENGTH_SHORT).show()
                    return false
                }
            }

            return true
        }

        fun validatePassword(password: String, context: Context): Boolean {
            if (password.isEmpty() && password.length < 6) {
                Toast.makeText(context, "Invalid password", Toast.LENGTH_SHORT).show()
                return false
            }

            return true
        }

        fun generateFirstLastName(email: String): MutableList<String> {
            val usernameValidation = email.split("@")
            val firstLastName = usernameValidation[0].split(".")
            return if (firstLastName.isNullOrEmpty() || firstLastName.size < 2) mutableListOf(usernameValidation[0], "") else mutableListOf(
                firstLastName[0][0].uppercaseChar() + firstLastName[0].substring(1),
                firstLastName[1][0].uppercaseChar() + firstLastName[1].substring(1)
            )
        }

        fun generateTimeString(timeInSeconds: Int): String {
            val minutes = timeInSeconds / 60
            val seconds = timeInSeconds % 60
            val minutesString = if (minutes < 10) "0$minutes" else minutes.toString()
            val secondsString = if (seconds < 10) "0$seconds" else seconds.toString()
            return "$minutesString:$secondsString"
        }

        fun isNumeric(string: String): Boolean {
            try {
                parseInt(string)
            } catch (e: NumberFormatException) {
                return false
            }

            return true
        }

        fun isDouble(string: String): Boolean {
            try {
                parseDouble(string)
            } catch (e: NumberFormatException) {
                return false
            }

            return true
        }
    }
}