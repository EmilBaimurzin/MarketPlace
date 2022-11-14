package com.marketplace.others

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marketplace.R
import com.marketplace.databinding.FragmentContainerBinding

class FragmentContainer: ViewBindingFragment<FragmentContainerBinding>(FragmentContainerBinding::inflate) {

    override fun onResume() {
        super.onResume()
        val navController = Navigation.findNavController(binding.fragmentContainer)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }
}