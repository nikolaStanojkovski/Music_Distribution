package com.musicdistribution.albumdistribution.data.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.musicdistribution.albumdistribution.data.domain.AlbumRoom
import com.musicdistribution.albumdistribution.data.domain.SongRoom

data class AlbumSong(
    @Embedded
    val albumRoom: AlbumRoom,
    @Relation(
        parentColumn = "id",
        entityColumn = "albumId"
    )
    val songRooms: MutableList<SongRoom> = mutableListOf()
)