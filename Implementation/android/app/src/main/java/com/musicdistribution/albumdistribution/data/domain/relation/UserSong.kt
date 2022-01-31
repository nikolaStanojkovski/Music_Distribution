package com.musicdistribution.albumdistribution.data.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.musicdistribution.albumdistribution.data.domain.Song
import com.musicdistribution.albumdistribution.data.domain.User

data class UserSong(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val songs: MutableList<Song> = mutableListOf()
)