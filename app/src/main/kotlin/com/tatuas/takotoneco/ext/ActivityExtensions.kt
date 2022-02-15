package com.tatuas.takotoneco.ext

import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun getNavController(navHostFragment: FragmentContainerView): NavController =
    navHostFragment.getFragment<NavHostFragment>().navController
