package com.musicdistribution.albumdistribution.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.musicdistribution.albumdistribution.data.domain.Album
import com.musicdistribution.albumdistribution.data.domain.Song
import com.musicdistribution.albumdistribution.data.domain.User
import com.musicdistribution.albumdistribution.data.room.dao.AlbumDao
import com.musicdistribution.albumdistribution.data.room.dao.SongDao
import com.musicdistribution.albumdistribution.data.room.dao.UserDao

@Database(entities = [User::class, Album::class, Song::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun songDao(): SongDao
    abstract fun albumDao(): AlbumDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = createInstance(context)
            }
            return instance!!
        }

        private fun createInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "albumdistribution.db").build()
        }
    }
}