package com.musicdistribution.streamingservice.data

import android.content.Context
import android.content.SharedPreferences

class SessionService(var context: Context) {

    private val PRIVATE_MODE: Int = 0
    var preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    var editor: SharedPreferences.Editor = preferences.edit()

    companion object {
        private const val PREF_NAME = "AlbumDistribution"
        private lateinit var sessionService: SessionService

        fun setSessionService(context: Context) {
            sessionService = SessionService(context)
        }

        fun save(key: String, value: String) {
            sessionService.editor.putString(key, value)
            sessionService.editor.apply()
        }

        fun read(key: String?): String? {
            return sessionService.preferences.getString(key, "")
        }
    }
}