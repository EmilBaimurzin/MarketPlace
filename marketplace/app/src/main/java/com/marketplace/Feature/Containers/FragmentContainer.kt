package com.marketplace.Feature.Containers

import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.marketplace.Common.ViewBindingFragment
import com.marketplace.databinding.FragmentContainerBinding

class FragmentContainer: ViewBindingFragment<FragmentContainerBinding>(FragmentContainerBinding::inflate) {

    override fun onResume() {
        super.onResume()
        val navController = Navigation.findNavController(binding.fragmentContainer)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }
}