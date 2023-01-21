package com.musicdistribution.streamingservice.util

import com.musicdistribution.streamingservice.constant.AlphabetConstants
import com.musicdistribution.streamingservice.constant.ApiConstants
import com.musicdistribution.streamingservice.constant.FileConstants
import com.musicdistribution.streamingservice.model.retrofit.core.Song

object ApiUtils {

    fun getSongCoverUrl(song: Song): String {
        return if (song.isASingle)
            "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM_SONGS}/${song.id}${FileConstants.PNG_EXTENSION}"
        else {
            if (song.album != null && song.album.id.isNotBlank()) {
                "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM_ALBUMS}/${song.album.id}${FileConstants.PNG_EXTENSION}"
            } else {
                AlphabetConstants.EMPTY_STRING
            }
        }
    }
}