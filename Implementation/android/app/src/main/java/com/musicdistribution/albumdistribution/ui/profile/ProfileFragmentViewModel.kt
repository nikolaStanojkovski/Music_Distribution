package com.musicdistribution.albumdistribution.ui.profile

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
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthDB
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthUser
import com.musicdistribution.albumdistribution.data.firebase.realtime.FirebaseRealtimeDB
import com.musicdistribution.albumdistribution.model.firebase.User
import com.musicdistribution.albumdistribution.model.retrofit.AlbumRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.ArtistRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.SongRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application

    private val albumCatalogApi: AlbumCatalogApi = AlbumCatalogApiClient.getAlbumCatalogApi()!!

    private var firebaseLiveData: MutableLiveData<User?> = MutableLiveData()
    private var songsLiveData: MutableLiveData<SongRetrofit?> = MutableLiveData()
    private var artistsLiveData: MutableLiveData<ArtistRetrofit?> = MutableLiveData()
    private var publishedSongsLiveData: MutableLiveData<MutableList<SongRetrofit?>> =
        MutableLiveData()
    private var publishedAlbumsLiveData: MutableLiveData<MutableList<AlbumRetrofit?>> =
        MutableLiveData()

    fun updateUserInfo(firstName: String, lastName: String) {
        updateFirebaseDb(firstName, lastName)
        // API not updated cause the users information is only valid for android version
    }

    private fun updateFirebaseDb(name: String, surname: String) {
        val currentUser = FirebaseAuthUser.user!!
        currentUser.name = name
        currentUser.surname = surname
        FirebaseRealtimeDB.usersReference.child(FirebaseAuthDB.firebaseAuth.currentUser!!.uid)
            .setValue(currentUser)
            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                if (task.isSuccessful) {
                    firebaseLiveData.value = currentUser
                }
            })
    }


    fun fetchFavoriteSongs() {
        FirebaseRealtimeDB.favouriteSongsReference.orderByChild("fanId")
            .equalTo(FirebaseAuthDB.firebaseAuth.currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists() && snapshot.value != null) {
                        val values = snapshot.value as HashMap<String, Any>
                        for (favSong in values.entries) {
                            val favouriteSong = favSong.value as HashMap<String, Any>
                            val songId = favouriteSong["songId"].toString()
                            albumCatalogApi.getSong(songId)
                                .enqueue(object : Callback<SongRetrofit?> {
                                    override fun onResponse(
                                        call: Call<SongRetrofit?>?,
                                        response: Response<SongRetrofit?>?
                                    ) {
                                        val song = response!!.body()
                                        if (song != null) {
                                            songsLiveData.value = song
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<SongRetrofit?>?,
                                        t: Throwable?
                                    ) {
                                        Toast.makeText(
                                            app,
                                            "There was a problem when trying to fetch song with id: $songId",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                })
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        app,
                        "There was a problem when trying to fetch favourite songs for current user",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    fun fetchFavouriteArtists() {
        FirebaseRealtimeDB.favouriteArtistsReference.orderByChild("followerId")
            .equalTo(FirebaseAuthDB.firebaseAuth.currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists() && snapshot.value != null) {
                        val values = snapshot.value as HashMap<String, Any>
                        for (favArtist in values.entries) {
                            val favouriteArtist = favArtist.value as HashMap<String, Any>
                            val artistId = favouriteArtist["followingId"].toString()
                            albumCatalogApi.getArtistById(artistId)
                                .enqueue(object : Callback<ArtistRetrofit?> {
                                    override fun onResponse(
                                        call: Call<ArtistRetrofit?>?,
                                        response: Response<ArtistRetrofit?>?
                                    ) {
                                        val artist = response!!.body()
                                        if (artist != null) {
                                            artistsLiveData.value = artist
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<ArtistRetrofit?>?,
                                        t: Throwable?
                                    ) {
                                        Toast.makeText(
                                            app,
                                            "There was a problem when trying to fetch artist with id: $artistId",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                })
                        }
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

    fun fetchPublishedAlbums() {
        albumCatalogApi.getAllAlbums().enqueue(object : Callback<ArrayList<AlbumRetrofit>> {
            override fun onResponse(
                call: Call<ArrayList<AlbumRetrofit>>?,
                response: Response<ArrayList<AlbumRetrofit>>
            ) {
                val albums = response.body()
                val publishedAlbums = mutableListOf<AlbumRetrofit?>()
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
                val publishedSongs = mutableListOf<SongRetrofit?>()
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


    fun getSongsLiveData(): MutableLiveData<SongRetrofit?> {
        return songsLiveData
    }

    fun getArtistsLiveData(): MutableLiveData<ArtistRetrofit?> {
        return artistsLiveData
    }

    fun getPublishedSongsLiveData(): MutableLiveData<MutableList<SongRetrofit?>> {
        return publishedSongsLiveData
    }

    fun getPublishedAlbumsLiveData(): MutableLiveData<MutableList<AlbumRetrofit?>> {
        return publishedAlbumsLiveData
    }
}