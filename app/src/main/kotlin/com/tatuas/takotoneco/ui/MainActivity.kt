package com.tatuas.takotoneco.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tatuas.takotoneco.R
import com.tatuas.takotoneco.databinding.ActivityMainBinding
import com.tatuas.takotoneco.ext.getNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(mainViewModel)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = getNavController(binding.navHostFragment)

        binding.bottomNav.setupWithNavController(navController)
        setupActionBarWithNavController(
            navController,
            AppBarConfiguration(
                setOf(
                    R.id.fragment_user_list,
                    R.id.fragment_about,
                )
            )
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
