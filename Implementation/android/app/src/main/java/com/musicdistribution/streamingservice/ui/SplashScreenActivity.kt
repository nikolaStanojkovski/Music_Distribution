package com.musicdistribution.streamingservice.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.musicdistribution.streamingservice.constant.MessageConstants
import com.musicdistribution.streamingservice.constant.TimeConstants
import com.musicdistribution.streamingservice.data.SessionService
import com.musicdistribution.streamingservice.ui.auth.AuthActivity
import streamingservice.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    @Suppress(MessageConstants.DEPRECATION)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SessionService.setSessionService(applicationContext)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }, TimeConstants.SPLASH_SCREEN_TIME)
    }
}