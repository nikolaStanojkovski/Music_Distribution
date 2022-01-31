package com.musicdistribution.albumdistribution.data.firebase.realtime

import com.google.firebase.database.FirebaseDatabase

class FirebaseRealtimeDB {
    companion object {
        var database = FirebaseDatabase.getInstance()

        var usersReference = database.getReference("users")
        var albumsReference = database.getReference("albums")
        var artistsReference = database.getReference("artists")
        var songsReference = database.getReference("songs")
    }
}