package com.musicdistribution.streamingservice.ui.home.item

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.musicdistribution.streamingservice.constants.EntityConstants
import com.musicdistribution.streamingservice.constants.ExceptionConstants
import com.musicdistribution.streamingservice.constants.PaginationConstants
import com.musicdistribution.streamingservice.data.api.StreamingServiceApiClient
import com.musicdistribution.streamingservice.data.api.core.AlbumServiceApi
import com.musicdistribution.streamingservice.data.api.core.ArtistServiceApi
import com.musicdistribution.streamingservice.data.api.core.SongServiceApi
import com.musicdistribution.streamingservice.model.retrofit.core.Album
import com.musicdistribution.streamingservice.model.retrofit.core.Artist
import com.musicdistribution.streamingservice.model.retrofit.core.Song
import com.musicdistribution.streamingservice.model.retrofit.response.AlbumResponse
import com.musicdistribution.streamingservice.model.retrofit.response.SongResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeItemFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application

    private val songServiceApi: SongServiceApi = StreamingServiceApiClient.getSongServiceApi()
    private val albumServiceApi: AlbumServiceApi = StreamingServiceApiClient.getAlbumServiceApi()
    private val artistServiceApi: ArtistServiceApi = StreamingServiceApiClient.getArtistServiceApi()

    private var songLiveData: MutableLiveData<Song?> = MutableLiveData()
    private var albumLiveData: MutableLiveData<Album?> = MutableLiveData()
    private var artistLiveData: MutableLiveData<Artist?> = MutableLiveData()

    private var artistAlbumsLiveData: MutableLiveData<MutableList<Album>> = MutableLiveData()
    private var artistSongsLiveData: MutableLiveData<MutableList<Song>> = MutableLiveData()
    private var albumSongsLiveData: MutableLiveData<MutableList<Song>> = MutableLiveData()

    fun fetchSong(id: String) {
        songServiceApi.findById(id).enqueue(object : Callback<Song?> {
            override fun onResponse(
                call: Call<Song?>?,
                response: Response<Song?>?
            ) {
                if (response?.body() == null) {
                    Toast.makeText(
                        app,
                        "${ExceptionConstants.SONG_FETCH_FAILED} $id",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    songLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Song?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    "${ExceptionConstants.SONG_FETCH_FAILED} $id",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun fetchAlbum(id: String) {
        albumServiceApi.findById(id).enqueue(object : Callback<Album?> {
            override fun onResponse(
                call: Call<Album?>?,
                response: Response<Album?>?
            ) {
                if (response?.body() == null) {
                    Toast.makeText(
                        app,
                        "${ExceptionConstants.ALBUM_FETCH_FAILED} $id",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    albumLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Album?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    "${ExceptionConstants.ALBUM_FETCH_FAILED} $id",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun fetchArtist(id: String) {
        artistServiceApi.findById(id).enqueue(object : Callback<Artist?> {
            override fun onResponse(
                call: Call<Artist?>?,
                response: Response<Artist?>?
            ) {
                if (response?.body() == null) {
                    Toast.makeText(
                        app,
                        "${ExceptionConstants.ARTIST_FETCH_FAILED} $id",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    artistLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Artist?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    "${ExceptionConstants.ARTIST_FETCH_FAILED} $id",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun fetchArtistAlbums(artistId: String) {
        albumServiceApi.search(
            EntityConstants.CREATOR_ID,
            artistId,
            PaginationConstants.DEFAULT_PAGE_NUMBER,
            PaginationConstants.DEFAULT_PAGE_SIZE
        ).enqueue(object : Callback<AlbumResponse?> {
            override fun onResponse(
                call: Call<AlbumResponse?>?,
                response: Response<AlbumResponse?>?
            ) {
                if (response?.body() == null || response.body()?.content == null) {
                    Toast.makeText(
                        app,
                        "${ExceptionConstants.ALBUMS_FETCH_FAILED} for the artist $artistId",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val albumResponse = response.body()
                    artistAlbumsLiveData.value = albumResponse!!.content.toMutableList()
                }
            }

            override fun onFailure(
                call: Call<AlbumResponse?>?,
                throwable: Throwable
            ) {
                Toast.makeText(
                    app,
                    "${ExceptionConstants.ALBUMS_FETCH_FAILED} for the artist $artistId",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun fetchArtistSongs(artistId: String) {
        songServiceApi.search(
            EntityConstants.CREATOR_ID,
            artistId,
            PaginationConstants.DEFAULT_PAGE_NUMBER,
            PaginationConstants.DEFAULT_PAGE_SIZE
        ).enqueue(object : Callback<SongResponse?> {
            override fun onResponse(
                call: Call<SongResponse?>?,
                response: Response<SongResponse?>?
            ) {
                if (response?.body() == null || response.body()?.content == null) {
                    Toast.makeText(
                        app,
                        "${ExceptionConstants.SONGS_FETCH_FAILED} for the artist $artistId",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val albumResponse = response.body()
                    artistSongsLiveData.value = albumResponse!!.content.toMutableList()
                }
            }

            override fun onFailure(
                call: Call<SongResponse?>?,
                throwable: Throwable
            ) {
                Toast.makeText(
                    app,
                    "${ExceptionConstants.SONGS_FETCH_FAILED} for the artist $artistId",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun fetchAlbumSongs(albumId: String) {
        songServiceApi.search(
            EntityConstants.ALBUM_ID,
            albumId,
            PaginationConstants.DEFAULT_PAGE_NUMBER,
            PaginationConstants.DEFAULT_PAGE_SIZE
        ).enqueue(object : Callback<SongResponse?> {
            override fun onResponse(
                call: Call<SongResponse?>?,
                response: Response<SongResponse?>?
            ) {
                if (response?.body() == null || response.body()?.content == null) {
                    Toast.makeText(
                        app,
                        "${ExceptionConstants.SONGS_FETCH_FAILED} for the album $albumId",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val albumResponse = response.body()
                    albumSongsLiveData.value = albumResponse!!.content.toMutableList()
                }
            }

            override fun onFailure(
                call: Call<SongResponse?>?,
                throwable: Throwable
            ) {
                Toast.makeText(
                    app,
                    "${ExceptionConstants.SONGS_FETCH_FAILED} for the album $albumId",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

//    fun favouriteSong(fanId: String, selectedSongId: String, like: Boolean) {
//        if (like) {
//            val favouriteSongMap = FavouriteSong(fanId, selectedSongId)
//            FirebaseRealtimeDB.favouriteSongsReference.child("/like-${fanId}-${selectedSongId}")
//                .setValue(favouriteSongMap)
//        } else {
//            FirebaseRealtimeDB.favouriteSongsReference.child("/like-${fanId}-${selectedSongId}")
//                .removeValue()
//        }
//    }

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

//
//    fun getArtistAlbumsLiveData(): MutableLiveData<MutableList<AlbumRetrofit>?> {
//        return artistAlbumsLiveData
//    }
//
//    fun getArtistSongsLiveData(): MutableLiveData<MutableList<SongRetrofit>?> {
//        return artistSongsLiveData
//    }
//
//    fun getAlbumSongsLiveData(): MutableLiveData<MutableList<SongRetrofit>?> {
//        return albumSongsLiveData
//    }

    fun getSongLiveData(): MutableLiveData<Song?> {
        return songLiveData
    }

    fun getAlbumLiveData(): MutableLiveData<Album?> {
        return albumLiveData
    }

    fun getArtistLiveData(): MutableLiveData<Artist?> {
        return artistLiveData
    }

    fun getArtistAlbumsLiveData(): MutableLiveData<MutableList<Album>> {
        return artistAlbumsLiveData
    }

    fun getArtistSongsLiveData(): MutableLiveData<MutableList<Song>> {
        return artistSongsLiveData
    }

    fun getAlbumSongsLiveData(): MutableLiveData<MutableList<Song>> {
        return albumSongsLiveData
    }

    fun clear() {
        this.songLiveData = MutableLiveData()
        this.albumLiveData = MutableLiveData()
        this.artistLiveData = MutableLiveData()

        this.artistAlbumsLiveData = MutableLiveData()
        this.artistSongsLiveData = MutableLiveData()
        this.albumSongsLiveData = MutableLiveData()

        // TODO: Add others here as well
    }
}