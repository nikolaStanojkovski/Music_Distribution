package com.musicdistribution.albumdistribution.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.musicdistribution.albumdistribution.data.domain.SongNotification

@Dao
abstract class SongNotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addSongNotification(songNotification: SongNotification)

    @Query("SELECT * FROM SongNotification as s WHERE s.songId = :songId AND s.creatorId = :creatorId")
    abstract fun readSongNotification(songId: String, creatorId: String): SongNotification?
}