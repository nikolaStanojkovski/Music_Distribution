package com.musicdistribution.albumdistribution.data.firebase.storage

import com.google.firebase.storage.FirebaseStorage

class FirebaseStorage {
    companion object {
        val storage = FirebaseStorage.getInstance()
    }
}