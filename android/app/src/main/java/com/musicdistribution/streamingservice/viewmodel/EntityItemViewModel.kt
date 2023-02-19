package com.musicdistribution.streamingservice.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.musicdistribution.streamingservice.constant.EntityConstants
import com.musicdistribution.streamingservice.constant.ExceptionConstants
import com.musicdistribution.streamingservice.constant.PaginationConstants
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

class EntityItemViewModel(application: Application) : AndroidViewModel(application) {

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
            arrayListOf(EntityConstants.CREATOR_ID),
            artistId,
            PaginationConstants.DEFAULT_PAGE_NUMBER,
            PaginationConstants.DEFAULT_PAGE_SIZE,
            PaginationConstants.DEFAULT_SORT_ORDER
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
            arrayListOf(EntityConstants.CREATOR_ID),
            artistId,
            PaginationConstants.DEFAULT_PAGE_NUMBER,
            PaginationConstants.DEFAULT_PAGE_SIZE,
            PaginationConstants.DEFAULT_SORT_ORDER
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
            arrayListOf(EntityConstants.ALBUM_ID),
            albumId,
            PaginationConstants.DEFAULT_PAGE_NUMBER,
            PaginationConstants.DEFAULT_PAGE_SIZE,
            PaginationConstants.DEFAULT_SORT_ORDER
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
    }
}