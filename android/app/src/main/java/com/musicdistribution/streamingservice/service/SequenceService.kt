package com.musicdistribution.streamingservice.service

import java.util.concurrent.atomic.AtomicInteger

object SequenceService {

    private val counter: AtomicInteger = AtomicInteger()

    fun nextValue(): Int {
        return counter.getAndIncrement()
    }
}