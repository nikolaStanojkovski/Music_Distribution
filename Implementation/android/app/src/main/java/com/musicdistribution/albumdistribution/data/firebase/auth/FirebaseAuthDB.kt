package com.musicdistribution.albumdistribution.data.firebase.auth

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthDB {
    companion object {
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

        const val GS_SIGN_IN = 120
        const val FB_SIGN_IN = 64206

        fun checkLogin(): Boolean {
            return firebaseAuth.currentUser != null
        }
    }
}