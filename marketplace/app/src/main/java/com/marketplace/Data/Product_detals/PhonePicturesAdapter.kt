package com.marketplace.Data.Product_detals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.marketplace.databinding.ItemPictureBinding

class PhonePicturesAdapter : RecyclerView.Adapter<PhonePicturesViewHolder>() {
    var items = mutableListOf<PhonePictures>()
    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhonePicturesViewHolder {
        val view = ItemPictureBinding.inflate(LayoutInflater.from(parent.context))

        view.root.layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)

        return PhonePicturesViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: PhonePicturesViewHolder, position: Int) {
        holder.bind(items[position])
    }
}