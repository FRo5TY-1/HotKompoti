package com.example.hotkompoti.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hotkompoti.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        navView = findViewById(R.id.bottomNavView)

        val controller = findNavController(R.id.nav_host_fragment)

        val appBarConfig = AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.profileFragment,
            R.id.notificationsFragment,
            R.id.menuFragment
        ))

        setupActionBarWithNavController(controller, appBarConfig)
        navView.setupWithNavController(controller)
    }
}