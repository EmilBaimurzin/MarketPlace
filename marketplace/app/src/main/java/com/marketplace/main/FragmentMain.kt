package com.marketplace.main

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marketplace.R
import com.marketplace.databinding.FragmentMainBinding
import com.marketplace.main.recycler_view.best_seller.BestSellerAdapter
import com.marketplace.main.recycler_view.categories.CategoriesAdapter
import com.marketplace.main.recycler_view.hot_sale.HotSaleAdapter
import com.marketplace.others.ViewBindingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragmentMain : ViewBindingFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var hotSaleAdapter: HotSaleAdapter
    private lateinit var bestSellerAdapter: BestSellerAdapter
    private val viewModel: FragmentMainViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCategoryList()
        initHotSaleList()
        initBestSellerList()

        binding.filterButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentMain_to_filterOptionDialog)
        }

        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.backGroundColor)

        lifecycleScope.launch {
            delay(10)
            val bottomNavigation =
                requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
            bottomNavigation.background =
                resources.getDrawable(R.drawable.two_round_corners_background)

        }

        viewModel.categoryList.observe(viewLifecycleOwner) {
            categoriesAdapter.items = it.toMutableList()
            categoriesAdapter.notifyDataSetChanged()
        }

        viewModel.hotSaleList.observe(viewLifecycleOwner) {
            hotSaleAdapter.items = it.toMutableList()
            hotSaleAdapter.notifyDataSetChanged()
        }

        viewModel.bestSellerList.observe(viewLifecycleOwner) {
            bestSellerAdapter.items = it.toMutableList()
            bestSellerAdapter.notifyDataSetChanged()
        }
    }

    private fun initCategoryList() {
        categoriesAdapter = CategoriesAdapter()
        with(binding.categoryRecyclerView) {
            adapter = categoriesAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            setHasFixedSize(true)
        }
        categoriesAdapter.itemClickListener = { position ->
            val oldCategory = viewModel.categoryList.value?.find { it.isOn }
            categoriesAdapter.items[oldCategory!!.id].isOn = false
            categoriesAdapter.items[position].isOn = true
            viewModel.changeCategory(categoriesAdapter.items)
        }
    }

    private fun initBestSellerList() {
        bestSellerAdapter = BestSellerAdapter()
        with(binding.bestSellerRecyclerView) {
            adapter = bestSellerAdapter
            layoutManager = GridLayoutManager(requireContext(), 2).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }
        bestSellerAdapter.items = viewModel.getBestSellerPreview()
        bestSellerAdapter.itemClickListener = {
            onItemClicked()
        }
    }

    private fun initHotSaleList() {
        hotSaleAdapter = HotSaleAdapter()
        binding.hotSaleViewPager.adapter = hotSaleAdapter
        hotSaleAdapter.itemClickListener = {
            onItemClicked()
        }
        hotSaleAdapter.items = viewModel.getHotSalesPreview()
    }

    private fun onItemClicked() {
        viewModel.getSmartphoneDetails {
            try {
                it?.let { details ->
                    lifecycleScope.launch(Dispatchers.Main) {
                        findNavController().navigate(FragmentMainDirections.actionFragmentMainToProductDetailsFragment(
                            details))
                    }
                } ?: Toast.makeText(requireContext(), "Unable to get Data", Toast.LENGTH_SHORT)
                    .show()
            } catch (_: Throwable) {
            }
        }
    }
}