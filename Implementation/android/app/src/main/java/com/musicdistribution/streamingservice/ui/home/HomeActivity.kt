package com.musicdistribution.streamingservice.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.musicdistribution.streamingservice.ui.auth.AuthActivity
import com.musicdistribution.streamingservice.util.AuthenticationUtils
import streamingservice.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (AuthenticationUtils.getAccessToken().isNotEmpty()) {
            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.navigationHostFragment) as NavHostFragment
            val navController = navHostFragment.navController
            bottomNavigationView.setupWithNavController(navController)
        } else {
            navigateOut()
        }
    }

    private fun navigateOut() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}