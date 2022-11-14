package com.marketplace.main.filter_options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.marketplace.databinding.BottomSheetDialogFilterBinding
import java.text.NumberFormat
import java.util.*

class FilterOptionDialog : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetDialogFilterBinding
    private val viewModel: FilterOptionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BottomSheetDialogFilterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapterBrand()
        setAdapterSize()

        binding.doneButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.closeButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.priceView.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("USD")
            format.format(value.toDouble())
        }

        binding.apply {
            priceTextView.text = viewModel.priceText
        }

        binding.priceView.addOnChangeListener { rangeSlider, value, fromUser ->
            binding.priceTextView.text = rangeSlider.values[0].toInt()
                .toString() + "$ - " + rangeSlider.values[1].toInt().toString() + "$"
        }
    }

    private fun setAdapterBrand() {
        val items = listOf("Apple", "Samsung", "Xiaomi", "Oppo")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        (binding.brandView.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun setAdapterSize() {
        val items = listOf("4.5 to 5.5 inches", "5.6 to 6.4 inches", "6.4+ inches")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        (binding.sizeView.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
}

class FilterOptionsViewModel: ViewModel() {
    var priceText = "400$ - 5000$"
}