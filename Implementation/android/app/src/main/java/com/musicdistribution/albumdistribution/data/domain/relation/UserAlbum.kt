package com.musicdistribution.albumdistribution.data.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.musicdistribution.albumdistribution.data.domain.AlbumRoom
import com.musicdistribution.albumdistribution.data.domain.UserRoom

data class UserAlbum(
    @Embedded
    val userRoom: UserRoom,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val albumRooms: MutableList<AlbumRoom> = mutableListOf()
)