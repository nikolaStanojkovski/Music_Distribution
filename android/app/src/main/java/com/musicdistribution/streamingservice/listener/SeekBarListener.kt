package com.musicdistribution.streamingservice.listener

interface SeekBarListener {
    fun onUpdate(progress: Int)
}