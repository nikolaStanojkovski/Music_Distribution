package com.musicdistribution.streamingservice.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.musicdistribution.streamingservice.constants.ExceptionConstants
import com.musicdistribution.streamingservice.constants.PaginationConstants
import com.musicdistribution.streamingservice.constants.SearchConstants
import com.musicdistribution.streamingservice.data.api.StreamingServiceApiClient
import com.musicdistribution.streamingservice.data.api.core.AlbumServiceApi
import com.musicdistribution.streamingservice.data.api.core.ArtistServiceApi
import com.musicdistribution.streamingservice.data.api.core.SongServiceApi
import com.musicdistribution.streamingservice.model.enums.Genre
import com.musicdistribution.streamingservice.model.retrofit.core.Album
import com.musicdistribution.streamingservice.model.retrofit.core.Artist
import com.musicdistribution.streamingservice.model.retrofit.core.Song
import com.musicdistribution.streamingservice.model.retrofit.response.AlbumResponse
import com.musicdistribution.streamingservice.model.retrofit.response.ArtistResponse
import com.musicdistribution.streamingservice.model.retrofit.response.SongResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application

    private val songServiceApi: SongServiceApi = StreamingServiceApiClient.getSongServiceApi()
    private val albumServiceApi: AlbumServiceApi = StreamingServiceApiClient.getAlbumServiceApi()
    private val artistServiceApi: ArtistServiceApi = StreamingServiceApiClient.getArtistServiceApi()

    private var genreSongResultsLiveData: MutableLiveData<MutableList<Song>> = MutableLiveData()
    private var genreAlbumResultsLiveData: MutableLiveData<MutableList<Album>> = MutableLiveData()

    private var searchSongsLiveData: MutableLiveData<MutableList<Song>> = MutableLiveData()
    private var searchAlbumsLiveData: MutableLiveData<MutableList<Album>> = MutableLiveData()
    private var searchArtistsLiveData: MutableLiveData<MutableList<Artist>> = MutableLiveData()

    fun fetchSearchData(searchTerm: String) {
        clearSearchData()

        artistServiceApi.search(
            arrayListOf(
                SearchConstants.ARTIST_FIRST_NAME,
                SearchConstants.ARTIST_LAST_NAME,
                SearchConstants.ARTIST_ART_NAME
            ),
            searchTerm,
            PaginationConstants.DEFAULT_PAGE_NUMBER,
            PaginationConstants.DEFAULT_PAGE_SIZE
        ).enqueue(object :
            Callback<ArtistResponse?> {
            override fun onResponse(
                call: Call<ArtistResponse?>?,
                response: Response<ArtistResponse?>
            ) {
                val artists = response.body()
                if (artists?.content != null
                    && artists.content.isNotEmpty()
                ) {
                    searchArtistsLiveData.value = artists.content.toMutableList()
                }
            }

            override fun onFailure(call: Call<ArtistResponse?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    ExceptionConstants.ARTISTS_FETCH_FAILED,
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        albumServiceApi.search(
            arrayListOf(
                SearchConstants.ALBUM_NAME,
                SearchConstants.ALBUM_INFO_ARTIST_NAME,
                SearchConstants.ALBUM_INFO_PRODUCER_NAME,
                SearchConstants.ALBUM_INFO_COMPOSER_NAME
            ),
            searchTerm,
            PaginationConstants.DEFAULT_PAGE_NUMBER,
            PaginationConstants.DEFAULT_PAGE_SIZE
        ).enqueue(object :
            Callback<AlbumResponse?> {
            override fun onResponse(
                call: Call<AlbumResponse?>?,
                response: Response<AlbumResponse?>
            ) {
                val albums = response.body()
                if (albums?.content != null
                    && albums.content.isNotEmpty()
                ) {
                    searchAlbumsLiveData.value = albums.content.toMutableList()
                }
            }

            override fun onFailure(call: Call<AlbumResponse?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    ExceptionConstants.ALBUMS_FETCH_FAILED,
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        songServiceApi.search(
            arrayListOf(
                SearchConstants.SONG_NAME
            ),
            searchTerm,
            PaginationConstants.DEFAULT_PAGE_NUMBER,
            PaginationConstants.DEFAULT_PAGE_SIZE
        ).enqueue(object :
            Callback<SongResponse?> {
            override fun onResponse(
                call: Call<SongResponse?>?,
                response: Response<SongResponse?>
            ) {
                val songs = response.body()
                if (songs?.content != null
                    && songs.content.isNotEmpty()
                ) {
                    searchSongsLiveData.value = songs.content.toMutableList()
                }
            }

            override fun onFailure(call: Call<SongResponse?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    ExceptionConstants.SONGS_FETCH_FAILED,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun fetchGenreData(genre: Genre) {
        clear()

        albumServiceApi.search(
            arrayListOf(
                SearchConstants.ALBUM_GENRE
            ),
            genre.toString(),
            PaginationConstants.DEFAULT_PAGE_NUMBER,
            PaginationConstants.DEFAULT_PAGE_SIZE
        ).enqueue(object :
            Callback<AlbumResponse?> {
            override fun onResponse(
                call: Call<AlbumResponse?>?,
                response: Response<AlbumResponse?>
            ) {
                val albums = response.body()
                if (albums?.content != null
                    && albums.content.isNotEmpty()
                ) {
                    genreAlbumResultsLiveData.value = albums.content.toMutableList()
                }
            }

            override fun onFailure(call: Call<AlbumResponse?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    ExceptionConstants.ALBUMS_FETCH_FAILED,
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        songServiceApi.search(
            arrayListOf(
                SearchConstants.SONG_GENRE
            ),
            genre.toString(),
            PaginationConstants.DEFAULT_PAGE_NUMBER,
            PaginationConstants.DEFAULT_PAGE_SIZE
        ).enqueue(object :
            Callback<SongResponse?> {
            override fun onResponse(
                call: Call<SongResponse?>?,
                response: Response<SongResponse?>
            ) {
                val songs = response.body()
                if (songs?.content != null
                    && songs.content.isNotEmpty()
                ) {
                    genreSongResultsLiveData.value = songs.content.toMutableList()
                }
            }

            override fun onFailure(call: Call<SongResponse?>?, throwable: Throwable) {
                Toast.makeText(
                    app,
                    ExceptionConstants.SONGS_FETCH_FAILED,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun getGenreSongResultsLiveData(): MutableLiveData<MutableList<Song>> {
        return genreSongResultsLiveData
    }

    fun getGenreAlbumResultsLiveData(): MutableLiveData<MutableList<Album>> {
        return genreAlbumResultsLiveData
    }

    fun getArtistsLiveData(): MutableLiveData<MutableList<Artist>> {
        return searchArtistsLiveData
    }

    fun getAlbumsLiveData(): MutableLiveData<MutableList<Album>> {
        return searchAlbumsLiveData
    }

    fun getSongsLiveData(): MutableLiveData<MutableList<Song>> {
        return searchSongsLiveData
    }

    private fun clearSearchData() {
        this.searchSongsLiveData = MutableLiveData()
        this.searchAlbumsLiveData = MutableLiveData()
        this.searchArtistsLiveData = MutableLiveData()
    }

    private fun clear() {
        clearSearchData()

        this.genreSongResultsLiveData = MutableLiveData()
        this.genreAlbumResultsLiveData = MutableLiveData()

        // TODO: Add others here as well
    }
}