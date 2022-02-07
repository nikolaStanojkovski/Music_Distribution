package com.musicdistribution.albumdistribution.ui.home

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.musicbution.albumdistribution.data.api.AlbumCatalogApiClient
import com.musicdistribution.albumdistribution.data.api.AlbumCatalogApi
import com.musicdistribution.albumdistribution.data.api.AlbumPublishingApi
import com.musicdistribution.albumdistribution.data.api.AlbumPublishingApiClient
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthUser
import com.musicdistribution.albumdistribution.model.retrofit.AlbumRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.ArtistRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.PublishedAlbumRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.SongRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application

    private val albumCatalogApi: AlbumCatalogApi = AlbumCatalogApiClient.getAlbumCatalogApi()!!

    private var artistsLiveData: MutableLiveData<MutableList<ArtistRetrofit>> = MutableLiveData()
    private var albumsLiveData: MutableLiveData<MutableList<AlbumRetrofit>> = MutableLiveData()
    private var songsLiveData: MutableLiveData<MutableList<SongRetrofit>> = MutableLiveData()

    private var publishedAlbumsLiveData: MutableLiveData<MutableList<AlbumRetrofit>> =
        MutableLiveData()
    private var publishedSongsLiveData: MutableLiveData<MutableList<SongRetrofit>> =
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


    fun getArtistsLiveData(): MutableLiveData<MutableList<ArtistRetrofit>> {
        return artistsLiveData
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

    fun getPublishedSongsLiveData(): MutableLiveData<MutableList<SongRetrofit>> {
        return publishedSongsLiveData
    }
}