package com.marketplace.main.recycler_view.best_seller

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marketplace.R
import com.marketplace.databinding.ItemBestSellerBinding

class BestSellerAdapter : RecyclerView.Adapter<BestSellerViewHolder>() {
    var items = mutableListOf<BestSellerItem>()
    var itemClickListener: ((Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerViewHolder {
        return BestSellerViewHolder(
            ItemBestSellerBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false), parent.context)
    }

    override fun onBindViewHolder(holder: BestSellerViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemClickListener = itemClickListener
    }

    override fun getItemCount(): Int = items.size
}

class BestSellerViewHolder(
    private val binding: ItemBestSellerBinding,
    private val context: Context,
) :
    RecyclerView.ViewHolder(binding.root) {
    var itemClickListener: ((Int) -> Unit)? = null

    fun bind(item: BestSellerItem) {
        binding.apply {
            nameTextView.text = item.title
            salePriceTextView.text = item.priceWithoutDiscount.toString() + "$"
            normalPriceTextView.text = item.discountPrice.toString() + "$"
            normalPriceTextView.paintFlags =
                normalPriceTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            Glide.with(context)
                .load(item.picture)
                .placeholder(R.drawable.placeholder_image)
                .into(previewImageView)

            if (item.isFavorites) {
                Glide.with(context)
                    .load(R.drawable.heart_on)
                    .into(favoritesImageView)
            } else {
                Glide.with(context)
                    .load(R.drawable.heart_off)
                    .into(favoritesImageView)
            }

            root.setOnClickListener {
                itemClickListener?.invoke(0)
            }
        }
    }
}

data class BestSellerItem(
    val title: String,
    val discountPrice: Int,
    val priceWithoutDiscount: Int,
    val isFavorites: Boolean,
    val picture: String,
)