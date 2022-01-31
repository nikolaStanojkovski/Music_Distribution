package com.musicdistribution.albumdistribution.data.domain.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.musicdistribution.albumdistribution.data.domain.Album
import com.musicdistribution.albumdistribution.data.domain.User

data class UserAlbum(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val albums: MutableList<Album> = mutableListOf()
)