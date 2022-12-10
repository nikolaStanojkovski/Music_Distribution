package com.musicdistribution.streamingservice.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.musicdistribution.streamingservice.data.domain.AlbumNotification
import com.musicdistribution.streamingservice.data.domain.SongNotification
import com.musicdistribution.streamingservice.data.room.dao.AlbumNotificationDao
import com.musicdistribution.streamingservice.data.room.dao.SongNotificationDao

@Database(entities = [SongNotification::class, AlbumNotification::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumNotificationDao(): AlbumNotificationDao
    abstract fun songNotificationDao(): SongNotificationDao

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
                AppDatabase::class.java, "streamingservice.db"
            ).build()
        }
    }
}