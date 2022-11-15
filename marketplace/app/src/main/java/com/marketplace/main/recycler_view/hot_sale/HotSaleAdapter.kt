package com.marketplace.main.recycler_view.hot_sale

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marketplace.R
import com.marketplace.databinding.ItemHotSalesBinding

class HotSaleAdapter() : RecyclerView.Adapter<HotSalesViewHolder>() {
    var items = mutableListOf<HotSaleItems>()
    var itemClickListener: (() -> Unit)? = null
    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotSalesViewHolder {
        val view = ItemHotSalesBinding.inflate(LayoutInflater.from(parent.context))

        view.root.layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)

        return HotSalesViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: HotSalesViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemClickListener = itemClickListener
    }
}

class HotSalesViewHolder(private val binding: ItemHotSalesBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {
    var itemClickListener: (() -> Unit)? = null
    fun bind(item: HotSaleItems) {
        if (!item.isPreview) {
            binding.apply {
                if (adapterPosition != 1) {
                    titleTextView.text = item.title
                    descriptionTextView.text = item.description
                }

                Glide.with(context)
                    .load(item.image)
                    .placeholder(R.drawable.placeholder_image)
                    .into(binding.ImageAsset)

                if (item.isNew) {
                    isNew.visibility = View.VISIBLE
                } else {
                    isNew.visibility = View.INVISIBLE
                }
                binding.buyButton.visibility = View.VISIBLE

                binding.root.setOnClickListener {
                    itemClickListener?.invoke()
                }

                binding.buyButton.setOnClickListener {
                    itemClickListener?.invoke()
                }
            }
        } else {
            Glide.with(context)
                .load(R.drawable.placeholder_image)
                .into(binding.ImageAsset)
            binding.buyButton.visibility = View.GONE
        }
    }
}

data class HotSaleItems(
    val isNew: Boolean,
    val title: String,
    val description: String,
    val image: String,
    val isPreview: Boolean = false
)

