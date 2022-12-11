package com.musicdistribution.streamingservice.ui.home.item

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.musicdistribution.streamingservice.model.firebase.FavouriteSong
import com.musicdistribution.streamingservice.model.firebase.User
import com.musicdistribution.streamingservice.model.retrofit.AlbumRetrofit
import com.musicdistribution.streamingservice.model.retrofit.ArtistRetrofit
import com.musicdistribution.streamingservice.model.retrofit.SongRetrofit

class HomeItemFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application

//    private val streamingServiceApi: StreamingServiceApi = StreamingServiceApiClient.getAlbumCatalogApi()!!

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
//        streamingServiceApi.getArtistById(id).enqueue(object : Callback<ArtistRetrofit?> {
//            override fun onResponse(
//                call: Call<ArtistRetrofit?>?,
//                response: Response<ArtistRetrofit?>
//            ) {
//                val artist = response.body()
//                if (artist != null) {
//                    artistsLiveData.value = artist!!
//                }
//            }
//
//            override fun onFailure(call: Call<ArtistRetrofit?>?, throwable: Throwable) {
//                Toast.makeText(
//                    app,
//                    "There was a problem when trying to fetch the artist with id: $id",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        })
    }

    fun favouriteSong(fanId: String, selectedSongId: String, like: Boolean) {
        if (like) {
            val favouriteSongMap = FavouriteSong(fanId, selectedSongId)
//            FirebaseRealtimeDB.favouriteSongsReference.child("/like-${fanId}-${selectedSongId}")
//                .setValue(favouriteSongMap)
        } else {
//            FirebaseRealtimeDB.favouriteSongsReference.child("/like-${fanId}-${selectedSongId}")
//                .removeValue()
        }
    }

//    fun updateFollowers(
//        followerId: String,
//        followingId: String,
//        follow: Boolean,
//        followingEmail: String
//    ) {
//        if (follow) {
//            val favouriteArtistMap = FavouriteArtist(followerId, followingId)
//            FirebaseRealtimeDB.favouriteArtistsReference.child("/follow-${followerId}-${followingId}")
//                .setValue(favouriteArtistMap)
//                .addOnCompleteListener(OnCompleteListener<Void?> { task ->
//                    if (task.isSuccessful) {
//                        FirebaseRealtimeDB.usersReference.child("/${followerId}").get()
//                            .addOnSuccessListener { user ->
//                                if (user.exists()) {
//                                    val userValue =
//                                        FirebaseAuthUser.getUser(user.value as HashMap<String, Any>)
//                                    userValue.noFollowing = userValue.noFollowing + 1
//                                    FirebaseRealtimeDB.usersReference.child("/${followerId}")
//                                        .setValue(userValue)
//                                }
//                            }
//                        FirebaseRealtimeDB.usersReference.orderByChild("email")
//                            .equalTo(followingEmail)
//                            .addListenerForSingleValueEvent(object : ValueEventListener {
//                                override fun onDataChange(snapshot: DataSnapshot) {
//                                    if (snapshot.exists() && snapshot.value != null) {
//                                        val values = snapshot.value as HashMap<String, Any>
//                                        val userInfo = values.entries.first()
//                                        val userValue =
//                                            FirebaseAuthUser.getUser(userInfo.value as HashMap<String, Any>)
//                                        userValue.noFollowers = userValue.noFollowers + 1
//                                        FirebaseRealtimeDB.usersReference.child("/${userInfo.key}")
//                                            .setValue(userValue)
//                                    }
//                                }
//
//                                override fun onCancelled(error: DatabaseError) {
//                                    Toast.makeText(
//                                        app,
//                                        "There was a problem when trying to fetch the user with email: $followingEmail",
//                                        Toast.LENGTH_LONG
//                                    ).show()
//                                }
//                            })
//                    }
//                })
//        } else {
//            FirebaseRealtimeDB.favouriteArtistsReference.child("/follow-${followerId}-${followingId}")
//                .removeValue().addOnCompleteListener(OnCompleteListener<Void?> { task ->
//                    if (task.isSuccessful) {
//                        FirebaseRealtimeDB.usersReference.child("/${followerId}").get()
//                            .addOnSuccessListener { user ->
//                                if (user.exists()) {
//                                    val userValue =
//                                        FirebaseAuthUser.getUser(user.value as HashMap<String, Any>)
//                                    userValue.noFollowing = userValue.noFollowing - 1
//                                    FirebaseRealtimeDB.usersReference.child("/${followerId}")
//                                        .setValue(userValue)
//                                }
//                            }
//                        FirebaseRealtimeDB.usersReference.orderByChild("email")
//                            .equalTo(followingEmail)
//                            .addListenerForSingleValueEvent(object : ValueEventListener {
//                                override fun onDataChange(snapshot: DataSnapshot) {
//                                    if (snapshot.exists() && snapshot.value != null) {
//                                        val values = snapshot.value as HashMap<String, Any>
//                                        val userInfo = values.entries.first()
//                                        val userValue =
//                                            FirebaseAuthUser.getUser(userInfo.value as HashMap<String, Any>)
//                                        userValue.noFollowers = userValue.noFollowers - 1
//                                        FirebaseRealtimeDB.usersReference.child("/${userInfo.key}")
//                                            .setValue(userValue)
//                                    }
//                                }
//
//                                override fun onCancelled(error: DatabaseError) {
//                                    Toast.makeText(
//                                        app,
//                                        "There was a problem when trying to fetch the user with email: $followingEmail",
//                                        Toast.LENGTH_LONG
//                                    ).show()
//                                }
//                            })
//                    }
//                })
//        }
//
//    }

    fun fetchArtistAlbumsApi(artistId: String) {
//        streamingServiceApi.getArtistAlbums(artistId)
//            .enqueue(object : Callback<ArrayList<AlbumRetrofit>> {
//                override fun onResponse(
//                    call: Call<ArrayList<AlbumRetrofit>>?,
//                    response: Response<ArrayList<AlbumRetrofit>>
//                ) {
//                    val albums = response.body()
//                    if (albums != null) {
//                        artistAlbumsLiveData.value = albums
//                    }
//                }
//
//                override fun onFailure(
//                    call: Call<ArrayList<AlbumRetrofit>>?,
//                    throwable: Throwable
//                ) {
//                    Toast.makeText(
//                        app,
//                        "There was a problem when trying to fetch the artist albums with id: $artistId",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            })
    }

    fun fetchArtistSongsApi(artistId: String) {
//        streamingServiceApi.getArtistSongs(artistId)
//            .enqueue(object : Callback<ArrayList<SongRetrofit>> {
//                override fun onResponse(
//                    call: Call<ArrayList<SongRetrofit>>?,
//                    response: Response<ArrayList<SongRetrofit>>
//                ) {
//                    val songs = response.body()
//                    if (songs != null && songs.isNotEmpty()) {
//                        artistSongsLiveData.value = songs
//                    }
//                }
//
//                override fun onFailure(
//                    call: Call<ArrayList<SongRetrofit>>?,
//                    throwable: Throwable
//                ) {
//                    Toast.makeText(
//                        app,
//                        "There was a problem when trying to fetch the artist songs with id: $artistId",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            })
    }

    fun fetchAlbumApi(id: String) {
//        streamingServiceApi.getAlbum(id).enqueue(object : Callback<AlbumRetrofit?> {
//            override fun onResponse(
//                call: Call<AlbumRetrofit?>?,
//                response: Response<AlbumRetrofit?>?
//            ) {
//                val album = response!!.body()
//                if (album != null) {
//                    albumsLiveData.value = album
//                }
//            }
//
//            override fun onFailure(call: Call<AlbumRetrofit?>?, throwable: Throwable) {
//                Toast.makeText(
//                    app,
//                    "There was a problem when trying to fetch the album with id: $id",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        })
    }

    fun fetchAlbumSongsApi(id: String) {
//        streamingServiceApi.getAlbumSongs(id).enqueue(object : Callback<ArrayList<SongRetrofit>> {
//            override fun onResponse(
//                call: Call<ArrayList<SongRetrofit>>?,
//                response: Response<ArrayList<SongRetrofit>>?
//            ) {
//                val songs = response!!.body()
//                if (songs != null && songs.isNotEmpty()) {
//                    albumSongsLiveData.value = songs
//                }
//            }
//
//            override fun onFailure(call: Call<ArrayList<SongRetrofit>>?, throwable: Throwable) {
//                Toast.makeText(
//                    app,
//                    "There was a problem when trying to fetch the songs from album with id: $id",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        })
    }

    fun fetchSongApi(id: String) {
//        streamingServiceApi.getSong(id).enqueue(object : Callback<SongRetrofit?> {
//            override fun onResponse(
//                call: Call<SongRetrofit?>?,
//                response: Response<SongRetrofit?>?
//            ) {
//                val song = response!!.body()
//                if (song != null) {
//                    songsLiveData.value = null
//                    songsLiveData.value = song
//                }
//            }
//
//            override fun onFailure(call: Call<SongRetrofit?>?, throwable: Throwable) {
//                Toast.makeText(
//                    app,
//                    "There was a problem when trying to fetch the song with id: $id",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        })
    }

    fun unPublishSong(selectedSongId: String) {
//        streamingServiceApi.unPublishSong(selectedSongId).enqueue(object : Callback<SongRetrofit?> {
//            override fun onResponse(
//                call: Call<SongRetrofit?>?,
//                response: Response<SongRetrofit?>
//            ) {
//                val song = response.body()
//                if (song != null) {
//                    clearFavouriteSOng(selectedSongId)
//                    Toast.makeText(
//                        app,
//                        "The song with id $selectedSongId is successfully unpublished",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<SongRetrofit?>?, throwable: Throwable) {
//                Toast.makeText(
//                    app,
//                    "There was a problem when trying to unpublish the song with id: $selectedSongId",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        })
    }

//    fun clearFavouriteSOng(selectedSongId: String) {
//        FirebaseRealtimeDB.favouriteSongsReference.orderByChild("songId").equalTo(selectedSongId)
//            .addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists() && snapshot.value != null) {
//                        snapshot.ref.setValue(null)
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                }
//            })
//    }


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