package com.musicdistribution.streamingservice.ui.home

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.musicdistribution.streamingservice.constants.ExceptionConstants
import com.musicdistribution.streamingservice.data.api.StreamingServiceApiClient
import com.musicdistribution.streamingservice.data.api.core.AlbumServiceApi
import com.musicdistribution.streamingservice.data.api.core.ArtistServiceApi
import com.musicdistribution.streamingservice.data.api.core.SongServiceApi
import com.musicdistribution.streamingservice.model.retrofit.core.Album
import com.musicdistribution.streamingservice.model.retrofit.core.Artist
import com.musicdistribution.streamingservice.model.retrofit.core.Song
import com.musicdistribution.streamingservice.model.retrofit.response.AlbumResponse
import com.musicdistribution.streamingservice.model.retrofit.response.ArtistResponse
import com.musicdistribution.streamingservice.model.retrofit.response.SongResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application
//    private val database = AppDatabase.getInstance(app)

//    private val streamingServiceApi: StreamingServiceApi = StreamingServiceApiClient.getAlbumCatalogApi()!!

    //    private var notificationsLiveData: MutableLiveData<String> = MutableLiveData()
    //    private var artistLiveData: MutableLiveData<ArtistRetrofit?> = MutableLiveData()

    private val songServiceApi: SongServiceApi = StreamingServiceApiClient.getSongServiceApi()
    private val albumServiceApi: AlbumServiceApi = StreamingServiceApiClient.getAlbumServiceApi()
    private val artistServiceApi: ArtistServiceApi = StreamingServiceApiClient.getArtistServiceApi()

    private var artistsLiveData: MutableLiveData<MutableList<Artist>> = MutableLiveData()
    private var albumsLiveData: MutableLiveData<MutableList<Album>> = MutableLiveData()
    private var songsLiveData: MutableLiveData<MutableList<Song>> = MutableLiveData()

    fun fetchAlbums() {
        albumServiceApi.findAll().enqueue(object : Callback<AlbumResponse?> {
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

    fun fetchArtists() {
        artistServiceApi.findAll().enqueue(object : Callback<ArtistResponse?> {
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

    fun fetchSongs() {
        songServiceApi.findAll().enqueue(object : Callback<SongResponse?> {
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

    fun checkNotifications() {
//        FirebaseRealtimeDB.favouriteArtistsReference.orderByChild("followerId")
//            .equalTo(FirebaseAuthDB.firebaseAuth.currentUser!!.uid)
//            .addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists() && snapshot.value != null) {
//                        val values = snapshot.value as HashMap<String, Object>
//                        val favouriteArtistIds = mutableListOf<String>()
//                        for (favArtist in values.entries) {
//                            val favouriteArtist = favArtist.value as HashMap<String, Object>
//                            val artistId = favouriteArtist["followingId"].toString()
//                            favouriteArtistIds.add(artistId)
//                        }
//
//                        checkSongNotifications(favouriteArtistIds)
//                        checkAlbumNotifications(favouriteArtistIds)
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(
//                        app,
//                        "There was a problem when trying to fetch favourite artists for current user",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            })
    }

//    private fun checkSongNotifications(favouriteArtistIds: MutableList<String>) {
//        for (artistId in favouriteArtistIds) {
//            FirebaseRealtimeDB.songNotificationsReference.orderByChild("creatorId")
//                .equalTo(artistId)
//                .addListenerForSingleValueEvent(object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        if (snapshot.exists() && snapshot.value != null) {
//                            val values = snapshot.value as HashMap<String, Object>
//                            for (notification in values.entries) {
//                                val songNotification = notification.value as HashMap<String, Object>
//                                val creatorName = songNotification["creatorName"].toString()
//                                val songId = songNotification["songId"].toString()
//                                val songName = songNotification["songName"].toString()
//                                CoroutineScope(Dispatchers.IO).launch {
//                                    if (database.songNotificationDao()
//                                            .readSongNotification(songId, artistId) == null
//                                    ) {
//                                        val songNotificationInsert =
//                                            SongNotification(
//                                                creatorId = artistId,
//                                                songId = songId,
//                                                creatorName = creatorName,
//                                                songName = songName
//                                            )
//                                        database.songNotificationDao()
//                                            .addSongNotification(songNotificationInsert)
//                                        withContext(Dispatchers.Main) {
//                                            var currentNotificationMessage =
//                                                if (notificationsLiveData.value != null)
//                                                    notificationsLiveData.value else ""
//                                            currentNotificationMessage += "New song with name '$songName' published by $creatorName\n"
//                                            notificationsLiveData.value =
//                                                currentNotificationMessage.toString()
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                    }
//                })
//        }
//    }

//    private fun checkAlbumNotifications(favouriteArtistIds: MutableList<String>) {
//        for (artistId in favouriteArtistIds) {
//            FirebaseRealtimeDB.albumNotificationsReference.orderByChild("creatorId")
//                .equalTo(artistId)
//                .addListenerForSingleValueEvent(object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        if (snapshot.exists() && snapshot.value != null) {
//                            val values = snapshot.value as HashMap<String, Object>
//                            for (notification in values.entries) {
//                                val albumNotification =
//                                    notification.value as HashMap<String, Object>
//                                val creatorName = albumNotification["creatorName"].toString()
//                                val albumId = albumNotification["albumId"].toString()
//                                val albumName = albumNotification["albumName"].toString()
//                                CoroutineScope(Dispatchers.IO).launch {
//                                    if (database.albumNotificationDao()
//                                            .readAlbumNotification(albumId, artistId) == null
//                                    ) {
//                                        val albumNotificationInsert =
//                                            AlbumNotification(
//                                                creatorId = artistId,
//                                                albumId = albumId,
//                                                creatorName = creatorName,
//                                                albumName = albumName
//                                            )
//                                        database.albumNotificationDao()
//                                            .addAlbumNotification(albumNotificationInsert)
//                                        withContext(Dispatchers.Main) {
//                                            var currentNotificationMessage =
//                                                if (notificationsLiveData.value != null)
//                                                    notificationsLiveData.value else ""
//                                            currentNotificationMessage += "New album with name '$albumName' published by $creatorName\n"
//                                            notificationsLiveData.value =
//                                                currentNotificationMessage.toString()
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                    }
//                })
//        }
//    }

    fun getAlbumsLiveData(): MutableLiveData<MutableList<Album>> {
        return albumsLiveData
    }

    fun getArtistsLiveData(): MutableLiveData<MutableList<Artist>> {
        return artistsLiveData
    }

    fun getSongsLiveData(): MutableLiveData<MutableList<Song>> {
        return songsLiveData
    }

    fun emptyData() {
        this.artistsLiveData = MutableLiveData()
        this.albumsLiveData = MutableLiveData()
        this.songsLiveData = MutableLiveData()
    }
}