package com.musicdistribution.albumdistribution.ui.auth

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.musicbution.albumdistribution.data.api.AlbumCatalogApiClient
import com.musicdistribution.albumdistribution.data.api.AlbumCatalogApi
import com.musicdistribution.albumdistribution.data.domain.Role
import com.musicdistribution.albumdistribution.data.domain.UserRoom
import com.musicdistribution.albumdistribution.data.room.AppDatabase
import com.musicdistribution.albumdistribution.model.retrofit.ArtistRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.ArtistRetrofitAuth
import com.musicdistribution.albumdistribution.model.retrofit.EmailDomain
import com.musicdistribution.albumdistribution.util.ValidationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application

    private val albumCatalogApi: AlbumCatalogApi = AlbumCatalogApiClient.getAlbumCatalogApi()!!
    private val database = AppDatabase.getInstance(app)
    private var usersLiveData: MutableLiveData<UserRoom> = MutableLiveData()
    private var artistsLiveData: MutableLiveData<ArtistRetrofit> = MutableLiveData()

    fun registerRoom(email: String, role: Role, firebaseUser: FirebaseUser) {
        val nameSurname = ValidationUtils.generateFirstLastName(email)
        val user = UserRoom(
            uid = firebaseUser.uid,
            name = nameSurname[0],
            surname = nameSurname[1],
            role = role,
            picture = "",
            noFollowing = 0L,
            noFollowers = 0L
        )
        CoroutineScope(Dispatchers.IO).launch {
            if (database.userDao().readByUid(firebaseUser.uid) == null) {
                database.userDao().createUser(user)
                withContext(Dispatchers.Main) {
                    usersLiveData.value = user
                }
            }
        }
    }

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
                                        artistsLiveData.value = artist
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

    fun getUsersLiveData(): MutableLiveData<UserRoom> {
        return usersLiveData
    }

    fun getArtistsLiveData(): MutableLiveData<ArtistRetrofit> {
        return artistsLiveData
    }
}