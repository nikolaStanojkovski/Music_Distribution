package com.musicdistribution.streamingservice.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.musicdistribution.streamingservice.model.retrofit.AlbumRetrofit
import com.musicdistribution.streamingservice.model.retrofit.ArtistRetrofit
import com.musicdistribution.streamingservice.model.retrofit.SongRetrofit

class ProfileFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application

//    private val streamingServiceApi: StreamingServiceApi = StreamingServiceApiClient.getAlbumCatalogApi()!!

    private var songsLiveData: MutableLiveData<SongRetrofit?> = MutableLiveData()
    private var artistsLiveData: MutableLiveData<ArtistRetrofit?> = MutableLiveData()
    private var publishedSongsLiveData: MutableLiveData<MutableList<SongRetrofit?>> =
        MutableLiveData()
    private var publishedAlbumsLiveData: MutableLiveData<MutableList<AlbumRetrofit?>> =
        MutableLiveData()

    fun updateUserInfo(firstName: String, lastName: String) {
//        updateFirebaseDb(firstName, lastName)
        // API not updated cause the users information is only valid for android version
    }

//    private fun updateFirebaseDb(name: String, surname: String) {
//        val currentUser = FirebaseAuthUser.user!!
//        currentUser.name = name
//        currentUser.surname = surname
//        FirebaseRealtimeDB.usersReference.child(FirebaseAuthDB.firebaseAuth.currentUser!!.uid)
//            .setValue(currentUser)
//            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
//                if (task.isSuccessful) {
//                    firebaseLiveData.value = currentUser
//                }
//            })
//    }

//    fun fetchFavoriteSongs() {
//        FirebaseRealtimeDB.favouriteSongsReference.orderByChild("fanId")
//            .equalTo(FirebaseAuthDB.firebaseAuth.currentUser!!.uid)
//            .addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists() && snapshot.value != null) {
//                        val values = snapshot.value as HashMap<String, Any>
//                        for (favSong in values.entries) {
//                            val favouriteSong = favSong.value as HashMap<String, Any>
//                            val songId = favouriteSong["songId"].toString()
//                            streamingServiceApi.getSong(songId)
//                                .enqueue(object : Callback<SongRetrofit?> {
//                                    override fun onResponse(
//                                        call: Call<SongRetrofit?>?,
//                                        response: Response<SongRetrofit?>?
//                                    ) {
//                                        val song = response!!.body()
//                                        if (song != null) {
//                                            songsLiveData.value = song
//                                        }
//                                    }
//
//                                    override fun onFailure(
//                                        call: Call<SongRetrofit?>?,
//                                        t: Throwable?
//                                    ) {
//                                        Toast.makeText(
//                                            app,
//                                            "There was a problem when trying to fetch song with id: $songId",
//                                            Toast.LENGTH_LONG
//                                        ).show()
//                                    }
//                                })
//                        }
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(
//                        app,
//                        "There was a problem when trying to fetch favourite songs for current user",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            })
//    }
//
//    fun fetchFavouriteArtists() {
//        FirebaseRealtimeDB.favouriteArtistsReference.orderByChild("followerId")
//            .equalTo(FirebaseAuthDB.firebaseAuth.currentUser!!.uid)
//            .addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists() && snapshot.value != null) {
//                        val values = snapshot.value as HashMap<String, Any>
//                        for (favArtist in values.entries) {
//                            val favouriteArtist = favArtist.value as HashMap<String, Any>
//                            val artistId = favouriteArtist["followingId"].toString()
//                            streamingServiceApi.getArtistById(artistId)
//                                .enqueue(object : Callback<ArtistRetrofit?> {
//                                    override fun onResponse(
//                                        call: Call<ArtistRetrofit?>?,
//                                        response: Response<ArtistRetrofit?>?
//                                    ) {
//                                        val artist = response!!.body()
//                                        if (artist != null) {
//                                            artistsLiveData.value = artist
//                                        }
//                                    }
//
//                                    override fun onFailure(
//                                        call: Call<ArtistRetrofit?>?,
//                                        t: Throwable?
//                                    ) {
//                                        Toast.makeText(
//                                            app,
//                                            "There was a problem when trying to fetch artist with id: $artistId",
//                                            Toast.LENGTH_LONG
//                                        ).show()
//                                    }
//                                })
//                        }
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
//    }


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