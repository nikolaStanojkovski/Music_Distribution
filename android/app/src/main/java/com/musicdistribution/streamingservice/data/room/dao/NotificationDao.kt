package com.musicdistribution.streamingservice.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.musicdistribution.streamingservice.model.domain.NotificationRoom

@Dao
abstract class NotificationDao {

    @Query("SELECT * FROM NotificationRoom as n WHERE n.listenerId = :listenerId AND n.publishingId = :publishingId AND n.creatorId = :creatorId")
    abstract fun find(
        listenerId: String,
        publishingId: String,
        creatorId: String
    ): NotificationRoom?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(notificationRoom: NotificationRoom)
}