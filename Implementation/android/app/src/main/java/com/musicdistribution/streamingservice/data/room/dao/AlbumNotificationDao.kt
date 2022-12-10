package com.musicdistribution.streamingservice.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.musicdistribution.streamingservice.data.domain.AlbumNotification

@Dao
abstract class AlbumNotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addAlbumNotification(albumNotification: AlbumNotification)

    @Query("SELECT * FROM AlbumNotification as a WHERE a.albumId = :albumId AND a.creatorId = :creatorId")
    abstract fun readAlbumNotification(albumId: String, creatorId: String): AlbumNotification?
}