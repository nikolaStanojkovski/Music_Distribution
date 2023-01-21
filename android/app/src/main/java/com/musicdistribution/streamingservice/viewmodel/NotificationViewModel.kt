package com.musicdistribution.streamingservice.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.musicdistribution.streamingservice.constant.EntityConstants
import com.musicdistribution.streamingservice.constant.ExceptionConstants
import com.musicdistribution.streamingservice.constant.PaginationConstants
import com.musicdistribution.streamingservice.data.api.StreamingServiceApiClient
import com.musicdistribution.streamingservice.data.api.core.NotificationServiceApi
import com.musicdistribution.streamingservice.data.room.AppDatabase
import com.musicdistribution.streamingservice.model.domain.NotificationRoom
import com.musicdistribution.streamingservice.model.retrofit.core.Notification
import com.musicdistribution.streamingservice.model.retrofit.response.NotificationResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application

    private val database = AppDatabase.getInstance(app)

    private val notificationServiceApi: NotificationServiceApi =
        StreamingServiceApiClient.getNotificationServiceApi()

    private var notificationLiveData: MutableLiveData<MutableList<Notification>> =
        MutableLiveData(mutableListOf())
    private var latestNotificationsEstimatedCount: Int = 0
    private var latestNotificationsCount: Int = 0
    private var latestNotifications: MutableList<Notification> = mutableListOf()

    fun fetchNotifications(listenerId: String) {
        notificationServiceApi.search(
            arrayListOf(EntityConstants.RECEIVER_ID),
            listenerId,
            PaginationConstants.DEFAULT_PAGE_NUMBER,
            PaginationConstants.DEFAULT_PAGE_SIZE
        ).enqueue(object : Callback<NotificationResponse?> {
            override fun onResponse(
                call: Call<NotificationResponse?>?,
                response: Response<NotificationResponse?>
            ) {
                val notifications = response.body()
                if (notifications?.content != null
                    && notifications.content.isNotEmpty()
                ) {
                    val receivedNotifications = notifications.content
                        .filter { !it.isReceived }
                        .toMutableList()

                    latestNotificationsEstimatedCount = receivedNotifications.size
                    receivedNotifications
                        .forEach { triggerNotification(it) }
                }
            }

            override fun onFailure(call: Call<NotificationResponse?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    ExceptionConstants.NOTIFICATIONS_FETCH_FAILED,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun triggerNotification(notification: Notification) {
        notificationServiceApi.receive(
            notification.listenerId,
            notification.publishingId
        ).enqueue(object : Callback<Notification?> {
            override fun onResponse(
                call: Call<Notification?>?,
                response: Response<Notification?>
            ) {
                CoroutineScope(Dispatchers.IO).launch {
                    latestNotificationsCount++
                    if (response.body() != null) {
                        saveNotification(notification)
                    }
                }
            }

            override fun onFailure(call: Call<Notification?>?, throwable: Throwable) {
                latestNotificationsCount++
                checkLiveDataUpdate()
                Toast.makeText(
                    app,
                    "${ExceptionConstants.NOTIFICATION_FETCH_FAILED} " +
                            "(${notification.listenerId}, ${notification.publishingId})",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun saveNotification(notification: Notification) {
        val listenerId = notification.listenerId
        val publishingId = notification.publishingId
        val creatorId = notification.creatorResponse.id

        if (database.notificationDao()
                .find(listenerId, publishingId, creatorId) == null
        ) {
            database.notificationDao()
                .insert(
                    NotificationRoom(
                        listenerId = listenerId,
                        creatorId = creatorId,
                        publishingId = publishingId
                    )
                )

            latestNotifications.add(notification)
            checkLiveDataUpdate()
        }
    }

    private fun checkLiveDataUpdate() {
        if (latestNotificationsCount == latestNotificationsEstimatedCount) {
            notificationLiveData.postValue(latestNotifications)
        }
    }

    fun getNotificationLiveData(): MutableLiveData<MutableList<Notification>> {
        return notificationLiveData
    }

    fun emptyData() {
        this.latestNotificationsCount = 0
        this.latestNotificationsEstimatedCount = 0
        this.latestNotifications = mutableListOf()
        this.notificationLiveData = MutableLiveData()
    }
}