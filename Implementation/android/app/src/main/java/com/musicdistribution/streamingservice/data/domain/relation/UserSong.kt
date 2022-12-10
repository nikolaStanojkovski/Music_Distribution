package com.musicdistribution.streamingservice.data.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.musicdistribution.streamingservice.data.domain.SongRoom
import com.musicdistribution.streamingservice.data.domain.UserRoom

data class UserSong(
    @Embedded
    val userRoom: UserRoom,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val songRooms: MutableList<SongRoom> = mutableListOf()
)