package com.musicdistribution.albumdistribution.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthDB
import com.musicdistribution.albumdistribution.databinding.ActivityAuthBinding
import com.musicdistribution.albumdistribution.ui.home.HomeActivity
import com.musicdistribution.albumdistribution.util.InternetUtils

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (InternetUtils.isOffline(this)) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
        }

        if (FirebaseAuthDB.checkLogin()) {
            navigateOut()
        }

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun navigateOut() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}