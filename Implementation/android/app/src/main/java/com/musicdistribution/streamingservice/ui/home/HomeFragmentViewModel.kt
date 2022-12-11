package com.musicdistribution.streamingservice.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.musicdistribution.streamingservice.data.room.AppDatabase
import com.musicdistribution.streamingservice.model.retrofit.UserAuth

class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application
    private val database = AppDatabase.getInstance(app)

//    private val streamingServiceApi: StreamingServiceApi = StreamingServiceApiClient.getAlbumCatalogApi()!!
    private var notificationsLiveData: MutableLiveData<String> = MutableLiveData()

//    private var artistsLiveData: MutableLiveData<MutableList<ArtistRetrofit>> = MutableLiveData()
//    private var artistLiveData: MutableLiveData<ArtistRetrofit?> = MutableLiveData()
//    private var albumsLiveData: MutableLiveData<MutableList<AlbumRetrofit>> = MutableLiveData()
//    private var songsLiveData: MutableLiveData<MutableList<SongRetrofit>> = MutableLiveData()
//
//    private var publishedAlbumsLiveData: MutableLiveData<MutableList<AlbumRetrofit>> =
//        MutableLiveData()
//    private var publishedSongsLiveData: MutableLiveData<MutableList<SongRetrofit>> =
//        MutableLiveData()

    fun fetchArtists() {
//        streamingServiceApi.getArtistsPage().enqueue(object : Callback<ArrayList<ArtistRetrofit>> {
//            override fun onResponse(
//                call: Call<ArrayList<ArtistRetrofit>>?,
//                response: Response<ArrayList<ArtistRetrofit>>
//            ) {
//                val artists = response.body()
//                if (!artists.isNullOrEmpty()) {
//                    artistsLiveData.value = artists.toMutableList()
//                }
//            }
//
//            override fun onFailure(call: Call<ArrayList<ArtistRetrofit>>?, throwable: Throwable) {
//                Toast.makeText(
//                    app,
//                    "There was a problem when trying to fetch artists",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        })
    }

    fun fetchArtist(userAuth: UserAuth) {
//        streamingServiceApi.getArtistByEmail(userAuth)
//            .enqueue(object : Callback<ArtistRetrofit?> {
//                override fun onResponse(
//                    call: Call<ArtistRetrofit?>?,
//                    response: Response<ArtistRetrofit?>
//                ) {
//                    val artist = response.body()
//                    if (artist != null) {
//                        artistLiveData.value = artist
//                    }
//                }
//
//                override fun onFailure(call: Call<ArtistRetrofit?>?, throwable: Throwable) {
//                    Toast.makeText(
//                        app,
//                        "There was a problem when trying to fetch the artist with mail ${userAuth.username + "@" + userAuth.emailDomain.toString() + ".com"}",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            })
    }

    fun fetchAlbums() {
//        streamingServiceApi.getAlbumsPage().enqueue(object : Callback<ArrayList<AlbumRetrofit>> {
//            override fun onResponse(
//                call: Call<ArrayList<AlbumRetrofit>>?,
//                response: Response<ArrayList<AlbumRetrofit>>
//            ) {
//                val albums = response.body()
//                if (!albums.isNullOrEmpty()) {
//                    albumsLiveData.value = albums.toMutableList()
//                }
//            }
//
//            override fun onFailure(call: Call<ArrayList<AlbumRetrofit>>?, throwable: Throwable) {
//                Toast.makeText(
//                    app,
//                    "There was a problem when trying to fetch albums",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        })
    }

    fun fetchSongs() {
//        streamingServiceApi.getSongsPage().enqueue(object : Callback<ArrayList<SongRetrofit>> {
//            override fun onResponse(
//                call: Call<ArrayList<SongRetrofit>>?,
//                response: Response<ArrayList<SongRetrofit>>
//            ) {
//                val songs = response.body()
//                if (!songs.isNullOrEmpty()) {
//                    songsLiveData.value = songs.toMutableList()
//                }
//            }
//
//            override fun onFailure(call: Call<ArrayList<SongRetrofit>>?, throwable: Throwable) {
//                Toast.makeText(
//                    app,
//                    "There was a problem when trying to fetch songs",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        })
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

    fun getNotificationsLivedata(): MutableLiveData<String> {
        return notificationsLiveData
    }

//    fun getArtistsLiveData(): MutableLiveData<MutableList<ArtistRetrofit>> {
//        return artistsLiveData
//    }
//
//    fun getArtistLiveData(): MutableLiveData<ArtistRetrofit?> {
//        return artistLiveData
//    }
//
//    fun getAlbumsLiveData(): MutableLiveData<MutableList<AlbumRetrofit>> {
//        return albumsLiveData
//    }
//
//    fun getSongsLiveData(): MutableLiveData<MutableList<SongRetrofit>> {
//        return songsLiveData
//    }
//
//    fun emptyData() {
//        this.artistsLiveData = MutableLiveData()
//        this.artistLiveData = MutableLiveData()
//        this.albumsLiveData = MutableLiveData()
//        this.songsLiveData = MutableLiveData()
//        this.publishedAlbumsLiveData = MutableLiveData()
//        this.publishedSongsLiveData = MutableLiveData()
//    }
}