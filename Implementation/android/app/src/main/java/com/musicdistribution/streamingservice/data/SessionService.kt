package com.musicdistribution.streamingservice.data

import android.content.Context
import android.content.SharedPreferences
import com.musicdistribution.streamingservice.constants.AlphabetConstants
import com.musicdistribution.streamingservice.constants.ExceptionConstants
import com.musicdistribution.streamingservice.constants.MessageConstants

class SessionService(var context: Context) {

    private val PRIVATE_MODE: Int = 0
    var preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    var editor: SharedPreferences.Editor = preferences.edit()

    companion object {
        private const val PREF_NAME = MessageConstants.APPLICATION_ID
        private var sessionService: SessionService? = null

        fun setSessionService(context: Context) {
            sessionService = SessionService(context)
        }

        fun save(key: String, value: String) {
            if (sessionService == null) {
                throw UnsupportedOperationException(ExceptionConstants.SESSION_NOT_INITIALIZED)
            } else {
                sessionService!!.editor.putString(key, value)
                sessionService!!.editor.apply()
            }
        }

        fun read(key: String?): String? {
            if (sessionService == null) {
                throw UnsupportedOperationException(ExceptionConstants.SESSION_NOT_INITIALIZED)
            } else {
                return sessionService!!.preferences.getString(key, AlphabetConstants.EMPTY_STRING)
            }
        }

        fun remove(key: String?) {
            if (sessionService == null) {
                throw UnsupportedOperationException(ExceptionConstants.SESSION_NOT_INITIALIZED)
            } else {
                sessionService!!.editor.remove(key)
                sessionService!!.editor.apply()
            }
        }
    }
}