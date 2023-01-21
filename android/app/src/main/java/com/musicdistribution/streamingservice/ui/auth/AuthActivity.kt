package com.musicdistribution.streamingservice.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.musicdistribution.streamingservice.constant.ApiConstants
import com.musicdistribution.streamingservice.constant.EntityConstants
import com.musicdistribution.streamingservice.constant.ExceptionConstants
import com.musicdistribution.streamingservice.service.SessionService
import com.musicdistribution.streamingservice.ui.home.HomeActivity
import com.musicdistribution.streamingservice.util.InternetUtils
import streamingservice.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (InternetUtils.isOffline(this)) {
            Toast.makeText(
                this,
                ExceptionConstants.NO_INTERNET_CONNECTION,
                Toast.LENGTH_LONG
            ).show()
        }

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (checkCachedUser()) {
            navigateOut()
        }
    }

    private fun checkCachedUser(): Boolean {
        val accessToken = SessionService.read(ApiConstants.ACCESS_TOKEN)
        val userId = SessionService.read(EntityConstants.USER_ID)
        return (accessToken != null && accessToken.isNotBlank()
                && userId != null && userId.isNotBlank())
    }

    private fun navigateOut() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}