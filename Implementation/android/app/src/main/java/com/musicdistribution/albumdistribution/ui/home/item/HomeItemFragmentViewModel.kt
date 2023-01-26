package com.musicdistribution.albumdistribution.ui.home.item

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.musicdistribution.albumdistribution.data.api.AlbumCatalogApi
import com.musicdistribution.albumdistribution.data.api.AlbumCatalogApiClient
import com.musicdistribution.albumdistribution.data.api.AlbumPublishingApi
import com.musicdistribution.albumdistribution.data.api.AlbumPublishingApiClient
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthUser
import com.musicdistribution.albumdistribution.data.firebase.realtime.FirebaseRealtimeDB
import com.musicdistribution.albumdistribution.model.firebase.FavouriteArtist
import com.musicdistribution.albumdistribution.model.firebase.FavouriteSong
import com.musicdistribution.albumdistribution.model.firebase.User
import com.musicdistribution.albumdistribution.model.retrofit.AlbumRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.ArtistRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.SongRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeItemFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application

    private val albumCatalogApi: AlbumCatalogApi = AlbumCatalogApiClient.getAlbumCatalogApi()!!
    private val albumPublishingApi: AlbumPublishingApi =
        AlbumPublishingApiClient.getMusicDistributorApi()!!

    private var artistsLiveData: MutableLiveData<ArtistRetrofit> = MutableLiveData()
    private var usersLiveData: MutableLiveData<User?> = MutableLiveData()
    private var artistAlbumsLiveData: MutableLiveData<MutableList<AlbumRetrofit>?> =
        MutableLiveData()
    private var artistSongsLiveData: MutableLiveData<MutableList<SongRetrofit>?> = MutableLiveData()
    private var albumsLiveData: MutableLiveData<AlbumRetrofit?> = MutableLiveData()
    private var albumSongsLiveData: MutableLiveData<MutableList<SongRetrofit>?> = MutableLiveData()
    private var songsLiveData: MutableLiveData<SongRetrofit?> = MutableLiveData()

    init {
        artistsLiveData = MutableLiveData()
        usersLiveData = MutableLiveData()
        artistAlbumsLiveData = MutableLiveData()
        artistSongsLiveData = MutableLiveData()
        albumsLiveData = MutableLiveData()
        albumSongsLiveData = MutableLiveData()
        songsLiveData = MutableLiveData()
    }

    fun fetchArtistApi(id: String) {
        albumCatalogApi.getArtistById(id).enqueue(object : Callback<ArtistRetrofit?> {
            override fun onResponse(
                call: Call<ArtistRetrofit?>?,
                response: Response<ArtistRetrofit?>
            ) {
                val artist = response.body()
                if (artist != null) {
                    artistsLiveData.value = artist!!
                }
            }

            override fun onFailure(call: Call<ArtistRetrofit?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    "There was a problem when trying to fetch the artist with id: $id",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun favouriteSong(fanId: String, selectedSongId: String, like: Boolean) {
        if (like) {
            val favouriteSongMap = FavouriteSong(fanId, selectedSongId)
            FirebaseRealtimeDB.favouriteSongsReference.child("/like-${fanId}-${selectedSongId}")
                .setValue(favouriteSongMap)
        } else {
            FirebaseRealtimeDB.favouriteSongsReference.child("/like-${fanId}-${selectedSongId}")
                .removeValue()
        }
    }

    fun updateFollowers(
        followerId: String,
        followingId: String,
        follow: Boolean,
        followingEmail: String
    ) {
        if (follow) {
            val favouriteArtistMap = FavouriteArtist(followerId, followingId)
            FirebaseRealtimeDB.favouriteArtistsReference.child("/follow-${followerId}-${followingId}")
                .setValue(favouriteArtistMap)
                .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                    if (task.isSuccessful) {
                        FirebaseRealtimeDB.usersReference.child("/${followerId}").get()
                            .addOnSuccessListener { user ->
                                if (user.exists()) {
                                    val userValue =
                                        FirebaseAuthUser.getUser(user.value as HashMap<String, Any>)
                                    userValue.noFollowing = userValue.noFollowing + 1
                                    FirebaseRealtimeDB.usersReference.child("/${followerId}")
                                        .setValue(userValue)
                                }
                            }
                        FirebaseRealtimeDB.usersReference.orderByChild("email")
                            .equalTo(followingEmail)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists() && snapshot.value != null) {
                                        val values = snapshot.value as HashMap<String, Any>
                                        val userInfo = values.entries.first()
                                        val userValue =
                                            FirebaseAuthUser.getUser(userInfo.value as HashMap<String, Any>)
                                        userValue.noFollowers = userValue.noFollowers + 1
                                        FirebaseRealtimeDB.usersReference.child("/${userInfo.key}")
                                            .setValue(userValue)
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Toast.makeText(
                                        app,
                                        "There was a problem when trying to fetch the user with email: $followingEmail",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            })
                    }
                })
        } else {
            FirebaseRealtimeDB.favouriteArtistsReference.child("/follow-${followerId}-${followingId}")
                .removeValue().addOnCompleteListener(OnCompleteListener<Void?> { task ->
                    if (task.isSuccessful) {
                        FirebaseRealtimeDB.usersReference.child("/${followerId}").get()
                            .addOnSuccessListener { user ->
                                if (user.exists()) {
                                    val userValue =
                                        FirebaseAuthUser.getUser(user.value as HashMap<String, Any>)
                                    userValue.noFollowing = userValue.noFollowing - 1
                                    FirebaseRealtimeDB.usersReference.child("/${followerId}")
                                        .setValue(userValue)
                                }
                            }
                        FirebaseRealtimeDB.usersReference.orderByChild("email")
                            .equalTo(followingEmail)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists() && snapshot.value != null) {
                                        val values = snapshot.value as HashMap<String, Any>
                                        val userInfo = values.entries.first()
                                        val userValue =
                                            FirebaseAuthUser.getUser(userInfo.value as HashMap<String, Any>)
                                        userValue.noFollowers = userValue.noFollowers - 1
                                        FirebaseRealtimeDB.usersReference.child("/${userInfo.key}")
                                            .setValue(userValue)
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Toast.makeText(
                                        app,
                                        "There was a problem when trying to fetch the user with email: $followingEmail",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            })
                    }
                })
        }

    }

    fun fetchArtistFirebase(email: String) {
        FirebaseRealtimeDB.usersReference.orderByChild("email").equalTo(email)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists() && snapshot.value != null) {
                        val value = snapshot.value as HashMap<String, Any>
                        usersLiveData.value =
                            FirebaseAuthUser.getUser(value.entries.first().value as HashMap<String, Any>)
                    } else {
                        usersLiveData.value = null
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        app,
                        "There was a problem when trying to fetch the user with email: $email",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    fun fetchArtistAlbumsApi(artistId: String) {
        albumCatalogApi.getArtistAlbums(artistId)
            .enqueue(object : Callback<ArrayList<AlbumRetrofit>> {
                override fun onResponse(
                    call: Call<ArrayList<AlbumRetrofit>>?,
                    response: Response<ArrayList<AlbumRetrofit>>
                ) {
                    val albums = response.body()
                    if (albums != null) {
                        artistAlbumsLiveData.value = albums
                    }
                }

                override fun onFailure(
                    call: Call<ArrayList<AlbumRetrofit>>?,
                    throwable: Throwable
                ) {
                    Toast.makeText(
                        app,
                        "There was a problem when trying to fetch the artist albums with id: $artistId",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    fun fetchArtistSongsApi(artistId: String) {
        albumCatalogApi.getArtistSongs(artistId)
            .enqueue(object : Callback<ArrayList<SongRetrofit>> {
                override fun onResponse(
                    call: Call<ArrayList<SongRetrofit>>?,
                    response: Response<ArrayList<SongRetrofit>>
                ) {
                    val songs = response.body()
                    if (songs != null && songs.isNotEmpty()) {
                        artistSongsLiveData.value = songs
                    }
                }

                override fun onFailure(
                    call: Call<ArrayList<SongRetrofit>>?,
                    throwable: Throwable
                ) {
                    Toast.makeText(
                        app,
                        "There was a problem when trying to fetch the artist songs with id: $artistId",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    fun fetchAlbumApi(id: String) {
        albumCatalogApi.getAlbum(id).enqueue(object : Callback<AlbumRetrofit?> {
            override fun onResponse(
                call: Call<AlbumRetrofit?>?,
                response: Response<AlbumRetrofit?>?
            ) {
                val album = response!!.body()
                if (album != null) {
                    albumsLiveData.value = album
                }
            }

            override fun onFailure(call: Call<AlbumRetrofit?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    "There was a problem when trying to fetch the album with id: $id",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun fetchAlbumSongsApi(id: String) {
        albumCatalogApi.getAlbumSongs(id).enqueue(object : Callback<ArrayList<SongRetrofit>> {
            override fun onResponse(
                call: Call<ArrayList<SongRetrofit>>?,
                response: Response<ArrayList<SongRetrofit>>?
            ) {
                val songs = response!!.body()
                if (songs != null && songs.isNotEmpty()) {
                    albumSongsLiveData.value = songs
                }
            }

            override fun onFailure(call: Call<ArrayList<SongRetrofit>>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    "There was a problem when trying to fetch the songs from album with id: $id",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun fetchSongApi(id: String) {
        albumCatalogApi.getSong(id).enqueue(object : Callback<SongRetrofit?> {
            override fun onResponse(
                call: Call<SongRetrofit?>?,
                response: Response<SongRetrofit?>?
            ) {
                val song = response!!.body()
                if (song != null) {
                    songsLiveData.value = null
                    songsLiveData.value = song
                }
            }

            override fun onFailure(call: Call<SongRetrofit?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    "There was a problem when trying to fetch the song with id: $id",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun unPublishSong(selectedSongId: String) {
        albumCatalogApi.unPublishSong(selectedSongId).enqueue(object : Callback<SongRetrofit?> {
            override fun onResponse(
                call: Call<SongRetrofit?>?,
                response: Response<SongRetrofit?>
            ) {
                val song = response.body()
                if (song != null) {
                    clearFavouriteSOng(selectedSongId)
                    Toast.makeText(
                        app,
                        "The song with id $selectedSongId is successfully unpublished",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<SongRetrofit?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    "There was a problem when trying to unpublish the song with id: $selectedSongId",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun unPublishAlbum(selectedAlbumId: String) {
        albumPublishingApi.unPublishAlbum(selectedAlbumId)
            .enqueue(object : Callback<Void> {
                override fun onResponse(
                    call: Call<Void>?,
                    response: Response<Void>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            app,
                            "The album with id $selectedAlbumId is successfully unpublished",
                            Toast.LENGTH_LONG
                        ).show()

                        albumCatalogApi.getAlbumSongs(selectedAlbumId)
                            .enqueue(object : Callback<ArrayList<SongRetrofit>> {
                                override fun onResponse(
                                    call: Call<ArrayList<SongRetrofit>>?,
                                    response: Response<ArrayList<SongRetrofit>>?
                                ) {
                                    val songs = response!!.body()
                                    if (!songs.isNullOrEmpty()) {
                                        for (item in songs) {
                                            clearFavouriteSOng(item.id)
                                        }
                                    }
                                }

                                override fun onFailure(
                                    call: Call<ArrayList<SongRetrofit>?>?,
                                    throwable: Throwable
                                ) {
                                }
                            })
                    }
                }

                override fun onFailure(call: Call<Void>?, throwable: Throwable) {
                    Toast.makeText(
                        app,
                        "There was a problem when trying to unpublish the album with id: $selectedAlbumId",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    fun clearFavouriteSOng(selectedSongId: String) {
        FirebaseRealtimeDB.favouriteSongsReference.orderByChild("songId").equalTo(selectedSongId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists() && snapshot.value != null) {
                        snapshot.ref.setValue(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }


    fun getArtistsLiveData(): MutableLiveData<ArtistRetrofit> {
        return artistsLiveData
    }

    fun getArtistAlbumsLiveData(): MutableLiveData<MutableList<AlbumRetrofit>?> {
        return artistAlbumsLiveData
    }

    fun getArtistSongsLiveData(): MutableLiveData<MutableList<SongRetrofit>?> {
        return artistSongsLiveData
    }

    fun getUsersLiveData(): MutableLiveData<User?> {
        return usersLiveData
    }

    fun getAlbumsLiveData(): MutableLiveData<AlbumRetrofit?> {
        return albumsLiveData
    }

    fun getAlbumSongsLiveData(): MutableLiveData<MutableList<SongRetrofit>?> {
        return albumSongsLiveData
    }

    fun getSongsLiveData(): MutableLiveData<SongRetrofit?> {
        return songsLiveData
    }

    fun clear() {
        this.artistsLiveData = MutableLiveData()
        this.artistAlbumsLiveData = MutableLiveData()
        this.artistSongsLiveData = MutableLiveData()
        this.usersLiveData = MutableLiveData()
        this.albumsLiveData = MutableLiveData()
        this.albumSongsLiveData = MutableLiveData()
        this.songsLiveData = MutableLiveData()
    }
}