package com.musicdistribution.albumdistribution.data.room.dao

import androidx.room.*
import com.musicdistribution.albumdistribution.data.domain.User
import com.musicdistribution.albumdistribution.data.domain.relation.UserAlbum
import com.musicdistribution.albumdistribution.data.domain.relation.UserAlbumSong
import com.musicdistribution.albumdistribution.data.domain.relation.UserSong

@Dao
abstract class UserDao {

    // Standard CRUD operations

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun createUser(user: User)

    @Query("SELECT * FROM User WHERE id = :id")
    abstract fun readUser(id: Long): User

    @Update
    abstract fun updateUser(user: User)

    @Delete
    abstract fun deleteUser(user: User)


    // Other specific operations

    @Query("SELECT * FROM User")
    abstract fun readAllUsers(): MutableList<User>

    @Query("SELECT * FROM User WHERE id = :id")
    abstract fun readAllUsersWithAlbums(id: Long): UserAlbum

    @Query("SELECT * FROM User WHERE id = :id")
    abstract fun readAllUsersWithSongs(id: Long): UserSong

    @Query("SELECT * FROM User WHERE id = :id")
    abstract fun readAllUsersWithAlbumsAndSongs(id: Long): UserAlbumSong
}