package com.marketplace.Feature.Cart

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marketplace.R
import com.marketplace.cart.CartAdapter
import com.marketplace.databinding.FragmentCartBinding
import com.marketplace.Common.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentCart : ViewBindingFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {
    private lateinit var cartAdapter: CartAdapter
    private val viewModel: CartViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.dark_blue)

        lifecycleScope.launch {
            delay(10)
            val bottomNavigation =
                requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
            bottomNavigation.setBackgroundColor(resources.getColor(R.color.dark_blue))

        }
        viewModel.list.observe(viewLifecycleOwner) {
            cartAdapter.items = it.toMutableList()
            cartAdapter.notifyDataSetChanged()
            binding.totalTextViewValue.text = viewModel.totalPrice.toString()
            binding.deliveryTextViewValue.text = viewModel.delivery
        }
    }

    private fun initAdapter() {
        cartAdapter = CartAdapter()
        with(binding.cartRecyclerView) {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }

        cartAdapter.itemClickListener = { position, value ->
            Log.e("add", "")
            if (value) {
                viewModel.add(position)
            } else {
                viewModel.remove(position)
            }
        }
    }
}
