package com.musicdistribution.albumdistribution.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.musicbution.albumdistribution.data.api.AlbumCatalogApiClient
import com.musicdistribution.albumdistribution.data.api.AlbumCatalogApi
import com.musicdistribution.albumdistribution.data.domain.Role
import com.musicdistribution.albumdistribution.data.domain.UserRoom
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthDB
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthUser
import com.musicdistribution.albumdistribution.data.firebase.realtime.FirebaseRealtimeDB
import com.musicdistribution.albumdistribution.data.room.AppDatabase
import com.musicdistribution.albumdistribution.model.firebase.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application

    private val albumCatalogApi: AlbumCatalogApi = AlbumCatalogApiClient.getAlbumCatalogApi()!!
    private val database = AppDatabase.getInstance(app)
    private var roomLiveData: MutableLiveData<UserRoom?> = MutableLiveData()
    private var firebaseLiveData: MutableLiveData<User?> = MutableLiveData()

    fun updateUserInfo(firstName: String, lastName: String) {
        updateFirebaseDb(firstName, lastName)
        updateRoomDb(firstName, lastName)
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
                    if(currentUser.role == Role.CREATOR) {
                        // TODO: Make api call
                    }
                }
            }
        }
    }

    fun getRoomLiveData(): MutableLiveData<UserRoom?> {
        return roomLiveData
    }

    fun getFirebaseLiveData(): MutableLiveData<User?> {
        return firebaseLiveData
    }
}