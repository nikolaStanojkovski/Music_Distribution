package com.musicdistribution.albumdistribution.ui

import android.app.LauncherActivity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.ui.home.HomeFragmentViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var builder: NotificationCompat.Builder

    private lateinit var homeFragmentViewModel: HomeFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.navigationHostFragment)

        bottomNavigationView.setupWithNavController(navController)

        homeFragmentViewModel =
            ViewModelProvider(this)[HomeFragmentViewModel::class.java]
        checkNotifications()
    }

    private fun checkNotifications() {
        homeFragmentViewModel.checkNotifications()

        homeFragmentViewModel.getNotificationsLivedata()
            .observe(this,
                { message ->
                    if (!message.isNullOrBlank()) {
                        showNotification(message)
                    }
                })
    }

    private fun showNotification(message: String) {
        val channelId = "com.musicdistribution.albumdistribution"
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(this, LauncherActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            notificationChannel =
                NotificationChannel(channelId, message, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLACK
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setContentTitle("Album Publishing")
                .setContentText("New Album/Song by favourite artist")
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        this.resources,
                        R.drawable.ic_launcher
                    )
                )
                .setStyle(
                    NotificationCompat.BigTextStyle().bigText(message)
                )
                .setContentIntent(pendingIntent)

            notificationManager.notify(1234, builder.build())
        }
    }
}