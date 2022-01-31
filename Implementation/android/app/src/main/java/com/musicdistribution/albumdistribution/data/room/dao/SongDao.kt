package com.musicdistribution.albumdistribution.data.room.dao

import androidx.room.*
import com.musicdistribution.albumdistribution.data.domain.Album
import com.musicdistribution.albumdistribution.data.domain.Song

@Dao
abstract class SongDao {

    // Standard CRUD operations

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun createSong(song: Song)

    @Query("SELECT * FROM Song WHERE id = :id")
    abstract fun readSong(id: Long): Album

    @Update
    abstract fun updateSong(song: Song)

    @Delete
    abstract fun deleteSong(song: Song)
}