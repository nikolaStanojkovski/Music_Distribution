package com.musicdistribution.albumdistribution.ui.home

import android.app.Application
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.musicbution.albumdistribution.data.api.AlbumCatalogApiClient
import com.musicdistribution.albumdistribution.data.api.AlbumCatalogApi
import com.musicdistribution.albumdistribution.data.api.AlbumPublishingApi
import com.musicdistribution.albumdistribution.data.api.AlbumPublishingApiClient
import com.musicdistribution.albumdistribution.data.domain.AlbumNotification
import com.musicdistribution.albumdistribution.data.domain.SongNotification
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthDB
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthUser
import com.musicdistribution.albumdistribution.data.firebase.realtime.FirebaseRealtimeDB
import com.musicdistribution.albumdistribution.data.firebase.storage.FirebaseStorage
import com.musicdistribution.albumdistribution.data.room.AppDatabase
import com.musicdistribution.albumdistribution.model.Tier
import com.musicdistribution.albumdistribution.model.retrofit.*
import com.musicdistribution.albumdistribution.util.TransactionUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application
    private val database = AppDatabase.getInstance(app)

    private val albumCatalogApi: AlbumCatalogApi = AlbumCatalogApiClient.getAlbumCatalogApi()!!
    private val albumPublishingApi: AlbumPublishingApi =
        AlbumPublishingApiClient.getMusicDistributorApi()!!
    private var notificationsLiveData: MutableLiveData<String> = MutableLiveData()

    private var artistsLiveData: MutableLiveData<MutableList<ArtistRetrofit>> = MutableLiveData()
    private var artistLiveData: MutableLiveData<ArtistRetrofit?> = MutableLiveData()
    private var albumsLiveData: MutableLiveData<MutableList<AlbumRetrofit>> = MutableLiveData()
    private var songsLiveData: MutableLiveData<MutableList<SongRetrofit>> = MutableLiveData()

    private var publishedAlbumsLiveData: MutableLiveData<MutableList<AlbumRetrofit>> =
        MutableLiveData()
    private var currentPublishedAlbumsLiveData: MutableLiveData<MutableList<PublishedAlbumRetrofit>> =
        MutableLiveData()
    private var publishedSongsLiveData: MutableLiveData<MutableList<SongRetrofit>> =
        MutableLiveData()
    private var musicDistributorsLiveData: MutableLiveData<MutableList<MusicDistributorRetrofit>> =
        MutableLiveData()

    fun fetchArtists() {
        albumCatalogApi.getArtistsPage().enqueue(object : Callback<ArrayList<ArtistRetrofit>> {
            override fun onResponse(
                call: Call<ArrayList<ArtistRetrofit>>?,
                response: Response<ArrayList<ArtistRetrofit>>
            ) {
                val artists = response.body()
                if (!artists.isNullOrEmpty()) {
                    artistsLiveData.value = artists.toMutableList()
                }
            }

            override fun onFailure(call: Call<ArrayList<ArtistRetrofit>>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    "There was a problem when trying to fetch artists",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun fetchArtist(artistRetrofitAuth: ArtistRetrofitAuth) {
        albumCatalogApi.getArtistByEmail(artistRetrofitAuth)
            .enqueue(object : Callback<ArtistRetrofit?> {
                override fun onResponse(
                    call: Call<ArtistRetrofit?>?,
                    response: Response<ArtistRetrofit?>
                ) {
                    val artist = response.body()
                    if (artist != null) {
                        artistLiveData.value = artist
                    }
                }

                override fun onFailure(call: Call<ArtistRetrofit?>?, throwable: Throwable) {
                    Toast.makeText(
                        app,
                        "There was a problem when trying to fetch the artist with mail ${artistRetrofitAuth.username + "@" + artistRetrofitAuth.emailDomain.toString() + ".com"}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    fun fetchAlbums() {
        albumCatalogApi.getAlbumsPage().enqueue(object : Callback<ArrayList<AlbumRetrofit>> {
            override fun onResponse(
                call: Call<ArrayList<AlbumRetrofit>>?,
                response: Response<ArrayList<AlbumRetrofit>>
            ) {
                val albums = response.body()
                if (!albums.isNullOrEmpty()) {
                    albumsLiveData.value = albums.toMutableList()
                }
            }

            override fun onFailure(call: Call<ArrayList<AlbumRetrofit>>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    "There was a problem when trying to fetch albums",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun fetchSongs() {
        albumCatalogApi.getSongsPage().enqueue(object : Callback<ArrayList<SongRetrofit>> {
            override fun onResponse(
                call: Call<ArrayList<SongRetrofit>>?,
                response: Response<ArrayList<SongRetrofit>>
            ) {
                val songs = response.body()
                if (!songs.isNullOrEmpty()) {
                    songsLiveData.value = songs.toMutableList()
                }
            }

            override fun onFailure(call: Call<ArrayList<SongRetrofit>>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    "There was a problem when trying to fetch songs",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun fetchMusicDistributors() {
        albumPublishingApi.getAllDistributors()
            .enqueue(object : Callback<ArrayList<MusicDistributorRetrofit>> {
                override fun onResponse(
                    call: Call<ArrayList<MusicDistributorRetrofit>>?,
                    response: Response<ArrayList<MusicDistributorRetrofit>>
                ) {
                    val distributors = response.body()
                    if (!distributors.isNullOrEmpty()) {
                        musicDistributorsLiveData.value = distributors
                    }
                }

                override fun onFailure(
                    call: Call<ArrayList<MusicDistributorRetrofit>>?,
                    throwable: Throwable
                ) {
                    Toast.makeText(
                        app,
                        "There was a problem when trying to fetch music distributors",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    fun fetchCurrentPublishedAlbums(artistId: String) {
        albumPublishingApi.getAllPublishedAlbumsByArtist(artistId)
            .enqueue(object : Callback<ArrayList<PublishedAlbumRetrofit>> {
                override fun onResponse(
                    call: Call<ArrayList<PublishedAlbumRetrofit>>?,
                    response: Response<ArrayList<PublishedAlbumRetrofit>>
                ) {
                    val publishedAlbums = response.body()
                    if (!publishedAlbums.isNullOrEmpty()) {
                        currentPublishedAlbumsLiveData.value = publishedAlbums
                    }
                }

                override fun onFailure(
                    call: Call<ArrayList<PublishedAlbumRetrofit>>?,
                    throwable: Throwable
                ) {
                    Toast.makeText(
                        app,
                        "There was a problem when trying to fetch published albums",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    fun fetchPublishedAlbums() {
        albumCatalogApi.getAllAlbums().enqueue(object : Callback<ArrayList<AlbumRetrofit>> {
            override fun onResponse(
                call: Call<ArrayList<AlbumRetrofit>>?,
                response: Response<ArrayList<AlbumRetrofit>>
            ) {
                val albums = response.body()
                val publishedAlbums = mutableListOf<AlbumRetrofit>()
                if (!albums.isNullOrEmpty()) {
                    for (item in albums) {
                        if (item.isPublished && item.creator.email == FirebaseAuthUser.user!!.email) {
                            publishedAlbums.add(item)
                        }
                    }
                    publishedAlbumsLiveData.value = publishedAlbums
                }
            }

            override fun onFailure(call: Call<ArrayList<AlbumRetrofit>>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    "There was a problem when trying to fetch published albums",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun fetchPublishedSongs() {
        albumCatalogApi.getAllSongs().enqueue(object : Callback<ArrayList<SongRetrofit>> {
            override fun onResponse(
                call: Call<ArrayList<SongRetrofit>>?,
                response: Response<ArrayList<SongRetrofit>>
            ) {
                val songs = response.body()
                val publishedSongs = mutableListOf<SongRetrofit>()
                if (!songs.isNullOrEmpty()) {
                    for (item in songs) {
                        if (item.isASingle && item.creator!!.email == FirebaseAuthUser.user!!.email) {
                            publishedSongs.add(item)
                        }
                    }
                    publishedSongsLiveData.value = publishedSongs
                }
            }

            override fun onFailure(call: Call<ArrayList<SongRetrofit>>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    "There was a problem when trying to fetch published songs",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun publishSong(songRetrofitCreate: SongRetrofitCreate, picturePath: Uri?) {
        albumCatalogApi.publishSong(songRetrofitCreate).enqueue(object : Callback<SongRetrofit?> {
            override fun onResponse(
                call: Call<SongRetrofit?>?,
                response: Response<SongRetrofit?>
            ) {
                val song = response.body()
                if (song != null) {
                    if (picturePath != null && song.isASingle) {
                        val songImagesRef =
                            FirebaseStorage.storage.reference.child("song-images/${song.id}.jpg")
                        songImagesRef.putFile(picturePath)
                    }
                    Toast.makeText(
                        app,
                        "The song with name ${song.songName} was successfully published",
                        Toast.LENGTH_LONG
                    ).show()
                    val notificationSong =
                        SongNotification(
                            creatorId = song.creator!!.id!!,
                            creatorName = song.creator.artistPersonalInfo.fullName,
                            songId = song.id,
                            songName = song.songName
                        )
                    FirebaseRealtimeDB.songNotificationsReference.child("/note-${song.creator.id!!}-${song.id}")
                        .setValue(notificationSong)
                }
            }

            override fun onFailure(call: Call<SongRetrofit?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    "There was a problem when trying to publish the song",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun raiseAlbumTier(publishedAlbumCreate: PublishedAlbumRetrofitCreate) {
        albumPublishingApi.raiseAlbumTier(publishedAlbumCreate).enqueue(object : Callback<Void> {
            override fun onResponse(
                call: Call<Void>?,
                response: Response<Void>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        app,
                        "The album's tier with name ${publishedAlbumCreate.albumName} was successfully raised",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    "There was a problem when trying to raise the tier of the album",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun publishAlbum(
        createAlbum: AlbumRetrofitCreate,
        musicPublisher: Pair<String, String>,
        tier: Tier,
        picturePath: Uri?
    ) {
        albumCatalogApi.createAlbum(createAlbum).enqueue(object : Callback<AlbumRetrofit?> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<AlbumRetrofit?>?,
                response: Response<AlbumRetrofit?>
            ) {
                val album = response.body()
                if (album != null) {
                    val publishedAlbumCreate = PublishedAlbumRetrofitCreate(
                        albumId = album.id,
                        albumName = album.albumName,
                        artistId = album.artistId,
                        artistInformation = album.artistName,
                        musicPublisherId = musicPublisher.first,
                        musicPublisherInfo = musicPublisher.second,
                        albumTier = tier.toString(),
                        subscriptionFee = TransactionUtils.calculateCost(tier),
                        transactionFee = 5.00
                    )
                    albumPublishingApi.publishAlbum(publishedAlbumCreate)
                        .enqueue(object : Callback<Void> {
                            override fun onResponse(
                                call: Call<Void?>?,
                                response: Response<Void?>
                            ) {
                                if (response.isSuccessful) {
                                    if (picturePath != null) {
                                        val albumImagesRef =
                                            FirebaseStorage.storage.reference.child("album-images/${album.id}.jpg")
                                        albumImagesRef.putFile(picturePath)
                                    }
                                    Toast.makeText(
                                        app,
                                        "The album with name ${album.albumName} was successfully published",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    val notificationAlbum =
                                        AlbumNotification(
                                            creatorId = album.creator.id!!,
                                            creatorName = album.creator.artistPersonalInfo.fullName,
                                            albumId = album.id,
                                            albumName = album.albumName
                                        )
                                    FirebaseRealtimeDB.albumNotificationsReference.child("/note-${album.creator.id}-${album.id}")
                                        .setValue(notificationAlbum)
                                }
                            }

                            override fun onFailure(
                                call: Call<Void>?,
                                throwable: Throwable
                            ) {
                                Toast.makeText(
                                    app,
                                    "There was a problem when trying to publish the album",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                }
            }

            override fun onFailure(call: Call<AlbumRetrofit?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    "There was a problem when trying to publish the song",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun checkNotifications() {
        FirebaseRealtimeDB.favouriteArtistsReference.orderByChild("followerId")
            .equalTo(FirebaseAuthDB.firebaseAuth.currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists() && snapshot.value != null) {
                        val values = snapshot.value as HashMap<String, Object>
                        val favouriteArtistIds = mutableListOf<String>()
                        for (favArtist in values.entries) {
                            val favouriteArtist = favArtist.value as HashMap<String, Object>
                            val artistId = favouriteArtist["followingId"].toString()
                            favouriteArtistIds.add(artistId)
                        }

                        checkSongNotifications(favouriteArtistIds)
                        checkAlbumNotifications(favouriteArtistIds)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        app,
                        "There was a problem when trying to fetch favourite artists for current user",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun checkSongNotifications(favouriteArtistIds: MutableList<String>) {
        for (artistId in favouriteArtistIds) {
            FirebaseRealtimeDB.songNotificationsReference.orderByChild("creatorId")
                .equalTo(artistId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists() && snapshot.value != null) {
                            val values = snapshot.value as HashMap<String, Object>
                            for (notification in values.entries) {
                                val songNotification = notification.value as HashMap<String, Object>
                                val creatorName = songNotification["creatorName"].toString()
                                val songId = songNotification["songId"].toString()
                                val songName = songNotification["songName"].toString()
                                CoroutineScope(Dispatchers.IO).launch {
                                    if (database.songNotificationDao()
                                            .readSongNotification(songId, artistId) == null
                                    ) {
                                        val songNotificationInsert =
                                            SongNotification(
                                                creatorId = artistId,
                                                songId = songId,
                                                creatorName = creatorName,
                                                songName = songName
                                            )
                                        database.songNotificationDao()
                                            .addSongNotification(songNotificationInsert)
                                        withContext(Dispatchers.Main) {
                                            var currentNotificationMessage =
                                                if (notificationsLiveData.value != null)
                                                    notificationsLiveData.value else ""
                                            currentNotificationMessage += "New song with name '$songName' published by $creatorName\n"
                                            notificationsLiveData.value =
                                                currentNotificationMessage.toString()
                                        }
                                    }
                                }
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                })
        }
    }

    private fun checkAlbumNotifications(favouriteArtistIds: MutableList<String>) {
        for (artistId in favouriteArtistIds) {
            FirebaseRealtimeDB.albumNotificationsReference.orderByChild("creatorId")
                .equalTo(artistId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists() && snapshot.value != null) {
                            val values = snapshot.value as HashMap<String, Object>
                            for (notification in values.entries) {
                                val albumNotification =
                                    notification.value as HashMap<String, Object>
                                val creatorName = albumNotification["creatorName"].toString()
                                val albumId = albumNotification["albumId"].toString()
                                val albumName = albumNotification["albumName"].toString()
                                CoroutineScope(Dispatchers.IO).launch {
                                    if (database.albumNotificationDao()
                                            .readAlbumNotification(albumId, artistId) == null
                                    ) {
                                        val albumNotificationInsert =
                                            AlbumNotification(
                                                creatorId = artistId,
                                                albumId = albumId,
                                                creatorName = creatorName,
                                                albumName = albumName
                                            )
                                        database.albumNotificationDao()
                                            .addAlbumNotification(albumNotificationInsert)
                                        withContext(Dispatchers.Main) {
                                            var currentNotificationMessage =
                                                if (notificationsLiveData.value != null)
                                                    notificationsLiveData.value else ""
                                            currentNotificationMessage += "New album with name '$albumName' published by $creatorName\n"
                                            notificationsLiveData.value =
                                                currentNotificationMessage.toString()
                                        }
                                    }
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
        }
    }

    fun getNotificationsLivedata(): MutableLiveData<String> {
        return notificationsLiveData
    }

    fun getArtistsLiveData(): MutableLiveData<MutableList<ArtistRetrofit>> {
        return artistsLiveData
    }

    fun getArtistLiveData(): MutableLiveData<ArtistRetrofit?> {
        return artistLiveData
    }

    fun getAlbumsLiveData(): MutableLiveData<MutableList<AlbumRetrofit>> {
        return albumsLiveData
    }

    fun getSongsLiveData(): MutableLiveData<MutableList<SongRetrofit>> {
        return songsLiveData
    }

    fun getPublishedAlbumsLiveData(): MutableLiveData<MutableList<AlbumRetrofit>> {
        return publishedAlbumsLiveData
    }

    fun getCurrentPublishedAlbumsLiveData(): MutableLiveData<MutableList<PublishedAlbumRetrofit>> {
        return currentPublishedAlbumsLiveData
    }

    fun getPublishedSongsLiveData(): MutableLiveData<MutableList<SongRetrofit>> {
        return publishedSongsLiveData
    }

    fun getDistributorsLiveData(): MutableLiveData<MutableList<MusicDistributorRetrofit>> {
        return musicDistributorsLiveData
    }

    fun emptyData() {
        this.artistsLiveData = MutableLiveData()
        this.artistLiveData = MutableLiveData()
        this.albumsLiveData = MutableLiveData()
        this.songsLiveData = MutableLiveData()
        this.publishedAlbumsLiveData = MutableLiveData()
        this.publishedSongsLiveData = MutableLiveData()
        this.musicDistributorsLiveData = MutableLiveData()
        this.currentPublishedAlbumsLiveData = MutableLiveData()
    }
}