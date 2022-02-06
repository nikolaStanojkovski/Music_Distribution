package com.musicdistribution.albumdistribution.model.retrofit

data class SongLength (val lengthInSeconds: Int) {
    companion object {
        fun from(length: Int): SongLength {
            return  SongLength(length)
        }
    }
}