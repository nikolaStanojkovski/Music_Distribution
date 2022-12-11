package com.musicdistribution.streamingservice.ui

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
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.musicdistribution.streamingservice.constants.NotificationConstants
import com.musicdistribution.streamingservice.ui.home.HomeFragmentViewModel
import streamingservice.R

@Suppress("deprecation")
class HomeActivity : AppCompatActivity() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var builder: NotificationCompat.Builder

    private lateinit var homeFragmentViewModel: HomeFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigationHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

//        homeFragmentViewModel =
//            ViewModelProvider(this)[HomeFragmentViewModel::class.java]
//        checkNotifications()
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
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(this, LauncherActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            notificationChannel =
                NotificationChannel(
                    NotificationConstants.CHANNEL_ID,
                    message,
                    NotificationManager.IMPORTANCE_HIGH
                )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLACK
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = NotificationCompat.Builder(this, NotificationConstants.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(NotificationConstants.CONTENT_TITLE)
                .setContentText(NotificationConstants.CONTENT_TEXT)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        this.resources,
                        R.mipmap.ic_launcher
                    )
                )
                .setStyle(
                    NotificationCompat.BigTextStyle().bigText(message)
                )
                .setContentIntent(pendingIntent)

            notificationManager.notify(NotificationConstants.ID, builder.build())
        }
    }
}