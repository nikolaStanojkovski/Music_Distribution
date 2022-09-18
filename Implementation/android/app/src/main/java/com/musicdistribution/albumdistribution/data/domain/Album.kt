package com.musicdistribution.albumdistribution.data.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Album(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val length: Long,
    val genre: Genre,
    val producer: String,
    val arranger: String,
    val composer: String,

    val userId: Long
)