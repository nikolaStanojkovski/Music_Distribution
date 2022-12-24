package com.musicdistribution.streamingservice.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.musicdistribution.streamingservice.constant.DatabaseConstants
import com.musicdistribution.streamingservice.model.domain.NotificationRoom
import com.musicdistribution.streamingservice.data.room.dao.NotificationDao

@Database(entities = [NotificationRoom::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao

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
                AppDatabase::class.java, DatabaseConstants.INSTANCE_NAME
            ).build()
        }
    }
}