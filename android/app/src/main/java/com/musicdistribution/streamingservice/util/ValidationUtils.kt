package com.musicdistribution.streamingservice.util

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import com.musicdistribution.streamingservice.constant.AlphabetConstants
import com.musicdistribution.streamingservice.constant.EmailConstants
import com.musicdistribution.streamingservice.constant.ExceptionConstants

object ValidationUtils {

    fun validateEmail(
        email: String,
        emailDomains: MutableList<String>,
        context: Context
    ): Boolean {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(
                context,
                ExceptionConstants.INVALID_EMAIL_ADDRESS,
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        val emailDomain = getEmailDomain(email)
        if (emailDomain.isEmpty() || !emailDomains.contains(emailDomain)) {
            Toast.makeText(
                context,
                ExceptionConstants.UNSUPPORTED_EMAIL_DOMAIN,
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        return true
    }

    fun validatePassword(password: String, context: Context): Boolean {
        if (password.isEmpty() || password.length < 6) {
            Toast.makeText(context, ExceptionConstants.INVALID_PASSWORD, Toast.LENGTH_SHORT)
                .show()
            return false
        }

        return true
    }

    fun getUsername(email: String): String {
        val parts = email.split(AlphabetConstants.AT_SIGN)
        return if (parts.isNotEmpty() && parts.size == 2
            && parts[0].isNotEmpty() && parts[1].isNotEmpty()
        )
            parts[0]
        else AlphabetConstants.EMPTY_STRING
    }

    fun getEmailDomain(email: String): String {
        val parts = email.split(AlphabetConstants.AT_SIGN)
        return if (parts.isNotEmpty() && parts.size == 2
            && parts[0].isNotEmpty() && parts[1].isNotEmpty()
            && parts[1].contains(EmailConstants.COM)
        )
            parts[1].replace(
                EmailConstants.COM,
                AlphabetConstants.EMPTY_STRING
            )
        else AlphabetConstants.EMPTY_STRING
    }
}