package com.musicdistribution.albumdistribution.data

import android.content.Context
import android.content.SharedPreferences

class SessionService(var context: Context) {
    var preferences: SharedPreferences
    var editor: SharedPreferences.Editor
    var PRIVATE_MODE: Int = 0

    init {
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = preferences.edit()
    }

    companion object {
        private val PREF_NAME = "AlbumDistribution"
        private var sessionService: SessionService? = null

        fun setSessionService(context: Context) {
            sessionService = SessionService(context)
        }

        fun save(key: String, value: String) {
            sessionService!!.editor.putString(key, value)
            sessionService!!.editor.apply()
        }

        fun read(key: String?): String? {
            return sessionService!!.preferences.getString(key, "")
        }
    }
}