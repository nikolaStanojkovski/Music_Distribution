package com.musicdistribution.albumdistribution.data.room.dao

import androidx.room.*
import com.musicdistribution.albumdistribution.data.domain.AlbumRoom
import com.musicdistribution.albumdistribution.data.domain.relation.AlbumSong

@Dao
abstract class AlbumDao {

    // Standard CRUD operations

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun createAlbum(albumRoom: AlbumRoom)

    @Query("SELECT * FROM AlbumRoom WHERE id = :id")
    abstract fun readAlbum(id: Long): AlbumRoom

    @Update
    abstract fun updateAlbum(albumRoom: AlbumRoom)

    @Delete
    abstract fun deleteAlbum(albumRoom: AlbumRoom)


    // Other specific operations

    @Query("SELECT * FROM AlbumRoom")
    abstract fun readAllAlbums(): MutableList<AlbumRoom>

    @Query("SELECT * FROM AlbumRoom WHERE id = :id")
    abstract fun readAllAlbumsWithSongs(id: Long): AlbumSong
}