package com.musicdistribution.albumdistribution.util

import android.content.Context
import android.util.Patterns
import android.widget.Toast

class ValidationUtils {
    companion object {
        fun validateUsername(email: String, context: Context): Boolean {
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(context, "Invalid e-mail address", Toast.LENGTH_SHORT).show()
                return false
            }

            return true
        }

        fun validatePassword(password: String, context: Context): Boolean {
            if (password.isEmpty()) {
                Toast.makeText(context, "Invalid e-mail address", Toast.LENGTH_SHORT).show()
                return false
            }

            return true
        }
    }
}