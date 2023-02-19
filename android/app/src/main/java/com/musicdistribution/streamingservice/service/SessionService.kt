package com.musicdistribution.streamingservice.service

import android.content.Context
import android.content.SharedPreferences
import com.musicdistribution.streamingservice.constant.AlphabetConstants
import com.musicdistribution.streamingservice.constant.ExceptionConstants
import com.musicdistribution.streamingservice.constant.MessageConstants

object SessionService {

    private const val PREF_NAME = MessageConstants.APPLICATION_ID
    private var preferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    fun setSessionService(context: Context) {
        preferences = context.getSharedPreferences(PREF_NAME, 0)
        editor = preferences!!.edit()
    }

    fun save(key: String, value: String) {
        if (preferences == null || editor == null) {
            throw UnsupportedOperationException(ExceptionConstants.SESSION_NOT_INITIALIZED)
        } else {
            editor!!.putString(key, value)
            editor!!.apply()
        }
    }

    fun read(key: String?): String? {
        if (preferences == null || editor == null) {
            throw UnsupportedOperationException(ExceptionConstants.SESSION_NOT_INITIALIZED)
        } else {
            return preferences!!.getString(key, AlphabetConstants.EMPTY_STRING)
        }
    }

    fun remove(key: String?) {
        if (preferences == null || editor == null) {
            throw UnsupportedOperationException(ExceptionConstants.SESSION_NOT_INITIALIZED)
        } else {
            editor!!.remove(key)
            editor!!.apply()
        }
    }
}