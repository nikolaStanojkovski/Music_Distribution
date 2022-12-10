package com.musicdistribution.streamingservice.model.retrofit

data class SongLength(val lengthInSeconds: Int) {
    companion object {
        fun from(length: Int): SongLength {
            return SongLength(length)
        }
    }
}