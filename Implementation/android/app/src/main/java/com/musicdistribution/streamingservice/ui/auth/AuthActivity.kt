package com.musicdistribution.streamingservice.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.musicdistribution.streamingservice.constants.ExceptionConstants
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
    }
}