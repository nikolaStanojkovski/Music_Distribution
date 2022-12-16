package com.musicdistribution.streamingservice.ui.profile

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.musicdistribution.streamingservice.constants.EntityConstants
import com.musicdistribution.streamingservice.constants.ExceptionConstants
import com.musicdistribution.streamingservice.data.SessionService
import com.musicdistribution.streamingservice.data.api.StreamingServiceApiClient
import com.musicdistribution.streamingservice.data.api.core.ListenerServiceApi
import com.musicdistribution.streamingservice.model.retrofit.core.Album
import com.musicdistribution.streamingservice.model.retrofit.core.Artist
import com.musicdistribution.streamingservice.model.retrofit.core.Listener
import com.musicdistribution.streamingservice.model.retrofit.core.Song
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application

    private val listenerServiceApi: ListenerServiceApi =
        StreamingServiceApiClient.getListenerServiceApi()

    private var songsLiveData: MutableLiveData<MutableList<Song>> = MutableLiveData()
    private var albumsLiveData: MutableLiveData<MutableList<Album>> = MutableLiveData()
    private var artistsLiveData: MutableLiveData<MutableList<Artist>> = MutableLiveData()

    fun fetchFavouritesData() {
        val userId = SessionService.read(EntityConstants.USER_ID)
        listenerServiceApi.findById(userId.toString())
            .enqueue(object : Callback<Listener?> {
                override fun onResponse(
                    call: Call<Listener?>?,
                    response: Response<Listener?>?
                ) {
                    if (response?.body() == null) {
                        Toast.makeText(
                            app,
                            "${ExceptionConstants.LISTENER_DATA_FETCH_FAILED} $userId",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val listener = response.body()
                        if (listener != null
                            && !listener.favouriteAlbums.isNullOrEmpty()
                            && !listener.favouriteArtists.isNullOrEmpty()
                            && !listener.favouriteSongs.isNullOrEmpty()
                        ) {
                            artistsLiveData.value = listener.favouriteArtists
                            albumsLiveData.value = listener.favouriteAlbums
                            songsLiveData.value = listener.favouriteSongs
                        } else {
                            Toast.makeText(
                                app,
                                "${ExceptionConstants.LISTENER_DATA_FETCH_FAILED} $userId",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<Listener?>?, throwable: Throwable) {
                    Toast.makeText(
                        app,
                        ExceptionConstants.ARTIST_FETCH_FAILED,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    fun getArtistsLiveData(): MutableLiveData<MutableList<Artist>> {
        return artistsLiveData
    }

    fun getAlbumsLiveData(): MutableLiveData<MutableList<Album>> {
        return albumsLiveData
    }

    fun getSongsLiveData(): MutableLiveData<MutableList<Song>> {
        return songsLiveData
    }
}