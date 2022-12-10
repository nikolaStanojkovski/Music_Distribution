package com.musicdistribution.streamingservice.data.room.dao

import androidx.room.*
import com.musicdistribution.streamingservice.data.domain.UserRoom
import com.musicdistribution.streamingservice.data.domain.relation.UserAlbum
import com.musicdistribution.streamingservice.data.domain.relation.UserAlbumSong
import com.musicdistribution.streamingservice.data.domain.relation.UserSong

@Dao
abstract class UserDao {

    // Standard CRUD operations

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun createUser(userRoom: UserRoom)

    @Query("SELECT * FROM UserRoom WHERE id = :id")
    abstract fun readUser(id: Long): UserRoom

    @Update
    abstract fun updateUser(userRoom: UserRoom)

    @Delete
    abstract fun deleteUser(userRoom: UserRoom)


    // Other specific operations

    @Query("SELECT * FROM UserRoom WHERE uid = :uid")
    abstract fun readByUid(uid: String): UserRoom?

    @Query("SELECT * FROM UserRoom")
    abstract fun readAllUsers(): MutableList<UserRoom>

    @Query("SELECT * FROM UserRoom WHERE id = :id")
    abstract fun readAllUsersWithAlbums(id: Long): UserAlbum

    @Query("SELECT * FROM UserRoom WHERE id = :id")
    abstract fun readAllUsersWithSongs(id: Long): UserSong

    @Query("SELECT * FROM UserRoom WHERE id = :id")
    abstract fun readAllUsersWithAlbumsAndSongs(id: Long): UserAlbumSong
}