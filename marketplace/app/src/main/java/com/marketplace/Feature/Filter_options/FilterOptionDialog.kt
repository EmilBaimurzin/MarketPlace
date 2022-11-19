package com.marketplace.Feature.Filter_options

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.slider.LabelFormatter
import com.marketplace.databinding.BottomSheetDialogFilterBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilterOptionDialog : BottomSheetDialogFragment() {
    @Inject lateinit var formatter: LabelFormatter
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

        binding.priceView.setLabelFormatter(formatter)

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
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, items)
        (binding.brandView.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun setAdapterSize() {
        val items = listOf("4.5 to 5.5 inches", "5.6 to 6.4 inches", "6.4+ inches")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, items)
        (binding.sizeView.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
}