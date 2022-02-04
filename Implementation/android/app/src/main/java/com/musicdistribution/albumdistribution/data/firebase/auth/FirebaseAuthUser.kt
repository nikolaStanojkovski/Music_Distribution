package com.musicdistribution.albumdistribution.data.firebase.auth

import com.google.firebase.database.DataSnapshot
import com.musicdistribution.albumdistribution.data.domain.Role
import com.musicdistribution.albumdistribution.model.firebase.User

class FirebaseAuthUser {
    companion object {
        var user: User? = null

        fun updateUser(snapshot: DataSnapshot) {
            val item = snapshot.value as HashMap<String, Object>
            val newUser = User(
                item["name"]!!.toString(),
                item["surname"]!!.toString(),
                item["email"]!!.toString(),
                Role.valueOf(item["role"]!!.toString()),
                item["noFollowers"]!!.toString().toLong(),
                item["noFollowing"]!!.toString().toLong()
            )
            user = newUser
        }
    }
}