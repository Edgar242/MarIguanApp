package com.mar_iguana.tours

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mar_iguana.tours.databinding.ActivityMainBinding
import com.mar_iguana.tours.ui.home.TourDetailFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home,
                R.id.navigation_wishlist,
                R.id.navigation_profile))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        // Make bottom navigation bar visible only on Top level navigation (home, wishlist, profile)
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

        // Get current fragment
        when(navHost?.childFragmentManager?.fragments?.get(0)) {
            is TourDetailFragment -> {
                return
            } else -> {
                binding.navView.visibility = View.VISIBLE
            }
        }
    }
}