package com.musicdistribution.albumdistribution.data.firebase.realtime

import com.google.firebase.database.FirebaseDatabase

class FirebaseRealtimeDB {
    companion object {
        var database = FirebaseDatabase.getInstance()

        var usersReference = database.getReference("users")
        var songNotificationsReference = database.getReference("song-notifications")
        var albumNotificationsReference = database.getReference("album-notifications")
        var favouriteArtistsReference = database.getReference("favourite-artists")
        var favouriteSongsReference = database.getReference("favourite-songs")
    }
}