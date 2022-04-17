package com.example.fitnessbag.presentation

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.fitnessbag.R

fun Activity.navigateTo(id: Int) {
    val navController =
        Navigation.findNavController(this, R.id.nav_host_fragment)
    navController.navigate(id)
}