package com.musicdistribution.albumdistribution.data.room.dao

import androidx.room.*
import com.musicdistribution.albumdistribution.data.domain.Album
import com.musicdistribution.albumdistribution.data.domain.User
import com.musicdistribution.albumdistribution.data.domain.relation.AlbumSong
import com.musicdistribution.albumdistribution.data.domain.relation.UserAlbum
import com.musicdistribution.albumdistribution.data.domain.relation.UserAlbumSong
import com.musicdistribution.albumdistribution.data.domain.relation.UserSong

@Dao
abstract class AlbumDao {

    // Standard CRUD operations

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun createAlbum(album: Album)

    @Query("SELECT * FROM Album WHERE id = :id")
    abstract fun readAlbum(id: Long): Album

    @Update
    abstract fun updateAlbum(album: Album)

    @Delete
    abstract fun deleteAlbum(album: Album)


    // Other specific operations

    @Query("SELECT * FROM Album")
    abstract fun readAllAlbums(): MutableList<Album>

    @Query("SELECT * FROM Album WHERE id = :id")
    abstract fun readAllAlbumsWithSongs(id: Long): AlbumSong
}