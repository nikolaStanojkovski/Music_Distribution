package com.musicdistribution.albumdistribution.data.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.musicdistribution.albumdistribution.data.domain.Album
import com.musicdistribution.albumdistribution.data.domain.Song

data class AlbumSong(
    @Embedded
    val album: Album,
    @Relation(
        parentColumn = "id",
        entityColumn = "albumId"
    )
    val songs: MutableList<Song> = mutableListOf()
)