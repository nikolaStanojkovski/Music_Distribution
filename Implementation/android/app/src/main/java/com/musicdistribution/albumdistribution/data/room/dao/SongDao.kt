package com.musicdistribution.albumdistribution.data.room.dao

import androidx.room.*
import com.musicdistribution.albumdistribution.data.domain.AlbumRoom
import com.musicdistribution.albumdistribution.data.domain.SongRoom

@Dao
abstract class SongDao {

    // Standard CRUD operations

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun createSong(songRoom: SongRoom)

    @Query("SELECT * FROM SongRoom WHERE id = :id")
    abstract fun readSong(id: Long): SongRoom

    @Update
    abstract fun updateSong(songRoom: SongRoom)

    @Delete
    abstract fun deleteSong(songRoom: SongRoom)
}