package com.musicdistribution.albumdistribution.ui.profile

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.musicbution.albumdistribution.data.api.AlbumCatalogApiClient
import com.musicdistribution.albumdistribution.data.api.AlbumCatalogApi
import com.musicdistribution.albumdistribution.data.domain.UserRoom
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthDB
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthUser
import com.musicdistribution.albumdistribution.data.firebase.realtime.FirebaseRealtimeDB
import com.musicdistribution.albumdistribution.data.room.AppDatabase
import com.musicdistribution.albumdistribution.model.firebase.User
import com.musicdistribution.albumdistribution.model.retrofit.ArtistRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.SongRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application

    private val albumCatalogApi: AlbumCatalogApi = AlbumCatalogApiClient.getAlbumCatalogApi()!!
    private val database = AppDatabase.getInstance(app)

    private var roomLiveData: MutableLiveData<UserRoom?> = MutableLiveData()
    private var firebaseLiveData: MutableLiveData<User?> = MutableLiveData()
    private var songsLiveData: MutableLiveData<SongRetrofit?> = MutableLiveData()
    private var artistsLiveData: MutableLiveData<ArtistRetrofit?> = MutableLiveData()

    fun updateUserInfo(firstName: String, lastName: String) {
        updateFirebaseDb(firstName, lastName)
        updateRoomDb(firstName, lastName)
        // API not updated cause the users information is only valid for android version
    }

    fun fetchFavoriteSongs() {
        FirebaseRealtimeDB.favouriteSongsReference.orderByChild("fanId")
            .equalTo(FirebaseAuthDB.firebaseAuth.currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists() && snapshot.value != null) {
                        val values = snapshot.value as HashMap<String, Object>
                        for (favSong in values.entries) {
                            val favouriteSong = favSong.value as HashMap<String, Object>
                            val songId = favouriteSong["songId"].toString()
                            albumCatalogApi.getSong(songId)
                                .enqueue(object : Callback<SongRetrofit> {
                                    override fun onResponse(
                                        call: Call<SongRetrofit>?,
                                        response: Response<SongRetrofit>?
                                    ) {
                                        val song = response!!.body()
                                        if (song != null) {
                                            songsLiveData.value = song
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<SongRetrofit>?,
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
                        val values = snapshot.value as HashMap<String, Object>
                        for (favArtist in values.entries) {
                            val favouriteArtist = favArtist.value as HashMap<String, Object>
                            val artistId = favouriteArtist["followingId"].toString()
                            albumCatalogApi.getArtist(artistId)
                                .enqueue(object : Callback<ArtistRetrofit> {
                                    override fun onResponse(
                                        call: Call<ArtistRetrofit>?,
                                        response: Response<ArtistRetrofit>?
                                    ) {
                                        val artist = response!!.body()
                                        if (artist != null) {
                                            artistsLiveData.value = artist
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<ArtistRetrofit>?,
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

    private fun updateRoomDb(name: String, surname: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val currentUser =
                database.userDao().readByUid(FirebaseAuthDB.firebaseAuth.currentUser!!.uid)
            if (currentUser != null) {
                currentUser.name = name
                currentUser.surname = surname
                database.userDao().updateUser(currentUser)
                withContext(Dispatchers.Main) {
                    roomLiveData.value = currentUser
                }
            }
        }
    }

    fun getSongsLiveData(): MutableLiveData<SongRetrofit?> {
        return songsLiveData
    }

    fun getArtistsLiveData(): MutableLiveData<ArtistRetrofit?> {
        return artistsLiveData
    }
}