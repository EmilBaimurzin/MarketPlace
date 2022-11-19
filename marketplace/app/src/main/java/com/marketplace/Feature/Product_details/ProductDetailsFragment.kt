package com.marketplace.Feature.Product_details

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marketplace.Common.ViewBindingFragment
import com.marketplace.Common.networking.SmartphoneDetailsNet
import com.marketplace.Data.Product_detals.PhonePictures
import com.marketplace.Data.Product_detals.PhonePicturesAdapter
import com.marketplace.R
import com.marketplace.databinding.FragmentProductDetailsBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductDetailsFragment :
    ViewBindingFragment<FragmentProductDetailsBinding>(FragmentProductDetailsBinding::inflate) {
    private lateinit var details: SmartphoneDetailsNet
    private val viewModel: ProductDetailsViewModel by viewModels()
    private val args: ProductDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        details = args.details
        setAdapter()
        setAttributes()
        checkROMState()
        checkColorState()

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cartButton.setOnClickListener {
            findNavController().popBackStack()
            Toast.makeText(requireContext(), "Product has been added to cart", Toast.LENGTH_SHORT)
                .show()
        }

        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.backGroundColor)

        lifecycleScope.launch {
            delay(10)
            val bottomNavigation =
                requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
            bottomNavigation.background = ResourcesCompat.getDrawable(resources,
                R.drawable.two_round_corners_background,
                null)
        }

        binding.firstColor.setOnClickListener {
            viewModel.colorState = true
            checkColorState()
        }

        binding.secondColor.setOnClickListener {
            viewModel.colorState = false
            checkColorState()
        }

        binding.firstROMText.setOnClickListener {
            viewModel.romState = true
            checkROMState()
        }

        binding.secondROMText.setOnClickListener {
            viewModel.romState = false
            checkROMState()
        }
    }

    private fun checkColorState() {
        if (viewModel.colorState) {
            binding.isFirstColor.setImageDrawable(ResourcesCompat.getDrawable(resources,
                R.drawable.check_vector,
                null))
            binding.isSecondColor.setImageDrawable(null)
        } else {
            binding.isSecondColor.setImageDrawable(ResourcesCompat.getDrawable(resources,
                R.drawable.check_vector,
                null))
            binding.isFirstColor.setImageDrawable(null)
        }
    }

    private fun checkROMState() {
        if (viewModel.romState) {
            binding.firstROMText.backgroundTintList =
                AppCompatResources.getColorStateList(requireContext(), R.color.orange)
            binding.firstROMText.setTextColor(resources.getColor(R.color.white))
            binding.secondROMText.backgroundTintList =
                AppCompatResources.getColorStateList(requireContext(), R.color.backGroundColor)
            binding.secondROMText.setTextColor(resources.getColor(R.color.icon_gray))
        } else {
            binding.secondROMText.backgroundTintList =
                AppCompatResources.getColorStateList(requireContext(), R.color.orange)
            binding.secondROMText.setTextColor(resources.getColor(R.color.white))
            binding.firstROMText.backgroundTintList =
                AppCompatResources.getColorStateList(requireContext(), R.color.backGroundColor)
            binding.firstROMText.setTextColor(resources.getColor(R.color.icon_gray))
        }
    }

    private fun setAttributes() {
        binding.apply {
            productNameTextView.text = details.title
            if (details.isFavorites) {
                favouritesView.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.heart_on,
                    null))
            } else {
                favouritesView.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.heart_off,
                    null))
            }

            repeat(details.rating.toInt()) {
                val star = ImageView(requireContext())
                star.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.star_vector,
                    null))
                ratingView.addView(star)

                if ((details.rating - (it + 1) == 0.5)) {
                    val halfStar = ImageView(requireContext())
                    halfStar.setImageDrawable(ResourcesCompat.getDrawable(resources,
                        R.drawable.half_star_vector,
                        null))
                    ratingView.addView(halfStar)
                }
            }

            RAMText.text = details.ssd
            ROMText.text = details.sd
            cameraText.text = details.camera
            CPUText.text = details.CPU

            val firstColor = Color.parseColor(details.color[0])
            isFirstColor.setBackgroundDrawable(ColorDrawable(firstColor))

            val secondColorDrawable = Color.parseColor(details.color[1])
            isSecondColor.setBackgroundDrawable(ColorDrawable(secondColorDrawable))

            firstROMText.text = (details.capacity[0].toInt() + 2).toString() + "GB"
            secondROMText.text = (details.capacity[1].toInt() + 4).toString() + "GB"

            cartButton.text = "Add to Cart " + details.price + "$"
        }
    }

    private fun setAdapter() {
        binding.picturesViewPager.adapter = PhonePicturesAdapter().apply {
            items = details.images.map {
                PhonePictures(it)
            }.toMutableList()
        }
    }
}