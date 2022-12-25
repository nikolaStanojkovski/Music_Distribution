package com.musicdistribution.streamingservice.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.musicdistribution.streamingservice.constant.EntityConstants
import com.musicdistribution.streamingservice.constant.ExceptionConstants
import com.musicdistribution.streamingservice.constant.PaginationConstants
import com.musicdistribution.streamingservice.data.api.StreamingServiceApiClient
import com.musicdistribution.streamingservice.data.api.core.AlbumServiceApi
import com.musicdistribution.streamingservice.data.api.core.ArtistServiceApi
import com.musicdistribution.streamingservice.data.api.core.NotificationServiceApi
import com.musicdistribution.streamingservice.data.api.core.SongServiceApi
import com.musicdistribution.streamingservice.data.room.AppDatabase
import com.musicdistribution.streamingservice.model.domain.NotificationRoom
import com.musicdistribution.streamingservice.model.retrofit.core.Album
import com.musicdistribution.streamingservice.model.retrofit.core.Artist
import com.musicdistribution.streamingservice.model.retrofit.core.Notification
import com.musicdistribution.streamingservice.model.retrofit.core.Song
import com.musicdistribution.streamingservice.model.retrofit.response.AlbumResponse
import com.musicdistribution.streamingservice.model.retrofit.response.ArtistResponse
import com.musicdistribution.streamingservice.model.retrofit.response.NotificationResponse
import com.musicdistribution.streamingservice.model.retrofit.response.SongResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application
    private val database = AppDatabase.getInstance(app)

    private val songServiceApi: SongServiceApi = StreamingServiceApiClient.getSongServiceApi()
    private val albumServiceApi: AlbumServiceApi = StreamingServiceApiClient.getAlbumServiceApi()
    private val artistServiceApi: ArtistServiceApi = StreamingServiceApiClient.getArtistServiceApi()
    private val notificationServiceApi: NotificationServiceApi =
        StreamingServiceApiClient.getNotificationServiceApi()

    private var artistsLiveData: MutableLiveData<MutableList<Artist>> = MutableLiveData()
    private var albumsLiveData: MutableLiveData<MutableList<Album>> = MutableLiveData()
    private var songsLiveData: MutableLiveData<MutableList<Song>> = MutableLiveData()
    private var notificationLiveData: MutableLiveData<Notification> =
        MutableLiveData()

    fun fetchAlbums(page: Int?) {
        albumServiceApi.findAll(
            page ?: PaginationConstants.DEFAULT_PAGE_NUMBER,
            PaginationConstants.DEFAULT_PAGE_SIZE
        ).enqueue(object : Callback<AlbumResponse?> {
            override fun onResponse(
                call: Call<AlbumResponse?>?,
                response: Response<AlbumResponse?>
            ) {
                val albums = response.body()
                if (albums?.content != null
                    && albums.content.isNotEmpty()
                ) {
                    albumsLiveData.value = albums.content.toMutableList()
                }
            }

            override fun onFailure(call: Call<AlbumResponse?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    ExceptionConstants.ALBUMS_FETCH_FAILED,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun fetchArtists(page: Int?) {
        artistServiceApi.findAll(
            page ?: PaginationConstants.DEFAULT_PAGE_NUMBER,
            PaginationConstants.DEFAULT_PAGE_SIZE
        ).enqueue(object : Callback<ArtistResponse?> {
            override fun onResponse(
                call: Call<ArtistResponse?>?,
                response: Response<ArtistResponse?>
            ) {
                val artists = response.body()
                if (artists?.content != null
                    && artists.content.isNotEmpty()
                ) {
                    artistsLiveData.value = artists.content.toMutableList()
                }
            }

            override fun onFailure(call: Call<ArtistResponse?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    ExceptionConstants.ARTISTS_FETCH_FAILED,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun fetchSongs(page: Int?) {
        songServiceApi.findAll(
            page ?: PaginationConstants.DEFAULT_PAGE_NUMBER,
            PaginationConstants.DEFAULT_PAGE_SIZE
        ).enqueue(object : Callback<SongResponse?> {
            override fun onResponse(
                call: Call<SongResponse?>?,
                response: Response<SongResponse?>
            ) {
                val songs = response.body()
                if (songs?.content != null
                    && songs.content.isNotEmpty()
                ) {
                    songsLiveData.value = songs.content.toMutableList()
                }
            }

            override fun onFailure(call: Call<SongResponse?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    ExceptionConstants.SONGS_FETCH_FAILED,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

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
                    notifications.content
                        .toMutableList()
                        .filter { !it.isReceived }
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
                if (response.body() != null) {
                    saveNotification(notification)
                }
            }

            override fun onFailure(call: Call<Notification?>?, throwable: Throwable) {
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

        CoroutineScope(Dispatchers.IO).launch {
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
                withContext(Dispatchers.Main) {
                    notificationLiveData.value = notification
                }
            }
        }
    }

    fun getAlbumsLiveData(): MutableLiveData<MutableList<Album>> {
        return albumsLiveData
    }

    fun getArtistsLiveData(): MutableLiveData<MutableList<Artist>> {
        return artistsLiveData
    }

    fun getSongsLiveData(): MutableLiveData<MutableList<Song>> {
        return songsLiveData
    }

    fun getNotificationLiveData(): MutableLiveData<Notification> {
        return notificationLiveData
    }

    fun emptyData() {
        this.artistsLiveData = MutableLiveData()
        this.albumsLiveData = MutableLiveData()
        this.songsLiveData = MutableLiveData()
        this.notificationLiveData = MutableLiveData()
    }
}