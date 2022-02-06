package com.musicdistribution.albumdistribution.ui.search

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.musicbution.albumdistribution.data.api.AlbumCatalogApiClient
import com.musicdistribution.albumdistribution.data.api.AlbumCatalogApi
import com.musicdistribution.albumdistribution.data.domain.Genre
import com.musicdistribution.albumdistribution.data.room.AppDatabase
import com.musicdistribution.albumdistribution.model.retrofit.AlbumRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.ArtistRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.SongRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application

    private val albumCatalogApi: AlbumCatalogApi = AlbumCatalogApiClient.getAlbumCatalogApi()!!
    private val database = AppDatabase.getInstance(app)

    private var albumsGenreLiveData: MutableLiveData<MutableList<AlbumRetrofit>?> =
        MutableLiveData()
    private var songsLiveData: MutableLiveData<MutableList<SongRetrofit>?> = MutableLiveData()
    private var albumsLiveData: MutableLiveData<MutableList<AlbumRetrofit>?> = MutableLiveData()
    private var artistsLiveData: MutableLiveData<MutableList<ArtistRetrofit>?> = MutableLiveData()

    fun fetchSearchData(searchTerm: String) {
        albumCatalogApi.searchArtists(searchTerm).enqueue(object :
            Callback<ArrayList<ArtistRetrofit>> {
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
                    "There was a problem when trying to search artists",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        albumCatalogApi.searchAlbums(searchTerm).enqueue(object :
            Callback<ArrayList<AlbumRetrofit>> {
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
                    "There was a problem when trying to search album",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        albumCatalogApi.searchSongs(searchTerm).enqueue(object :
            Callback<ArrayList<SongRetrofit>> {
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
                    "There was a problem when trying to search songs",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun fetchGenreData(genre: Genre) {
        albumCatalogApi.getAlbumsGenre(genre.toString()).enqueue(object :
            Callback<ArrayList<AlbumRetrofit>> {
            override fun onResponse(
                call: Call<ArrayList<AlbumRetrofit>>?,
                response: Response<ArrayList<AlbumRetrofit>>
            ) {
                val albums = response.body()
                if (!albums.isNullOrEmpty()) {
                    albumsGenreLiveData.value = albums.toMutableList()
                }
            }

            override fun onFailure(call: Call<ArrayList<AlbumRetrofit>>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    "There was a problem when trying to fetch albums by genre",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }


    fun getAlbumsGenreLiveData(): MutableLiveData<MutableList<AlbumRetrofit>?> {
        return albumsGenreLiveData
    }

    fun getArtistsLiveData(): MutableLiveData<MutableList<ArtistRetrofit>?> {
        return artistsLiveData
    }

    fun getAlbumsLiveData(): MutableLiveData<MutableList<AlbumRetrofit>?> {
        return albumsLiveData
    }

    fun getSongsLiveData(): MutableLiveData<MutableList<SongRetrofit>?> {
        return songsLiveData
    }
}