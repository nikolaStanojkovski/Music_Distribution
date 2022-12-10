package com.musicdistribution.streamingservice.data.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.musicdistribution.streamingservice.data.domain.AlbumRoom
import com.musicdistribution.streamingservice.data.domain.SongRoom

data class AlbumSong(
    @Embedded
    val albumRoom: AlbumRoom,
    @Relation(
        parentColumn = "id",
        entityColumn = "albumId"
    )
    val songRooms: MutableList<SongRoom> = mutableListOf()
)