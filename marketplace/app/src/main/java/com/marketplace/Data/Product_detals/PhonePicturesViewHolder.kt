package com.marketplace.Data.Product_detals

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marketplace.Data.Product_detals.PhonePictures
import com.marketplace.R
import com.marketplace.databinding.ItemPictureBinding

class PhonePicturesViewHolder(
    private val binding: ItemPictureBinding,
    private val context: Context,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: PhonePictures) {
        Glide.with(context)
            .load(item.picture)
            .placeholder(R.drawable.placeholder_image)
            .into(binding.picture)
    }
}

