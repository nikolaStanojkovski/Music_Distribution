package com.musicdistribution.streamingservice.ui.auth

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.musicdistribution.streamingservice.data.api.AlbumCatalogApi
import com.musicdistribution.streamingservice.data.api.AlbumCatalogApiClient
import com.musicdistribution.streamingservice.model.Role
import com.musicdistribution.streamingservice.model.retrofit.ArtistRetrofit
import com.musicdistribution.streamingservice.model.retrofit.ArtistRetrofitAuth
import com.musicdistribution.streamingservice.model.retrofit.EmailDomain
import com.musicdistribution.streamingservice.util.ValidationUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application

    private val albumCatalogApi: AlbumCatalogApi = AlbumCatalogApiClient.getAlbumCatalogApi()!!
    private var artistsLiveData: MutableLiveData<ArtistRetrofit> = MutableLiveData()

    fun registerApi(email: String, password: String, role: Role) {
        if (role == Role.CREATOR) {
            val nameSurname = ValidationUtils.generateFirstLastName(email)
            val artistRetrofit = ArtistRetrofitAuth(
                username = email.split("@")[0],
                emailDomain = EmailDomain.valueOf(email.split("@")[1].split(".")[0]),
                telephoneNumber = "[not-defined]",
                firstName = nameSurname[0],
                lastName = nameSurname[1],
                artName = "[not-defined]",
                password = password
            )
            albumCatalogApi.loginArtist(artistRetrofit).enqueue(object : Callback<ArtistRetrofit?> {
                override fun onResponse(
                    call: Call<ArtistRetrofit?>?,
                    response: Response<ArtistRetrofit?>
                ) {
                    val artistLogin = response.body()
                    if (artistLogin == null) {
                        albumCatalogApi.registerArtist(artistRetrofit)
                            .enqueue(object : Callback<ArtistRetrofit?> {
                                override fun onResponse(
                                    call: Call<ArtistRetrofit?>?,
                                    response: Response<ArtistRetrofit?>?
                                ) {
                                    val artist = response!!.body()
                                    if (artist != null) {
                                        artistsLiveData.value = artist!!
                                    }
                                }

                                override fun onFailure(
                                    call: Call<ArtistRetrofit?>?,
                                    t: Throwable?
                                ) {
                                    Toast.makeText(
                                        app,
                                        "There was a problem when trying to register the artist to the server 'Album Catalog'!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            })
                    } else {
                        Toast.makeText(
                            app,
                            "There is already an artist with that username and password!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ArtistRetrofit?>?, throwable: Throwable) {
                    Toast.makeText(
                        app,
                        "There is already an artist with that username and password!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }

    fun getArtistsLiveData(): MutableLiveData<ArtistRetrofit> {
        return artistsLiveData
    }
}