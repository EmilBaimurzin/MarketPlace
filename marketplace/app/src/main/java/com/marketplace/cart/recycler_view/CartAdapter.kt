package com.marketplace.cart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marketplace.R
import com.marketplace.cart.recycler_view.Basket
import com.marketplace.databinding.ItemCartBinding

class CartAdapter : RecyclerView.Adapter<CartViewHolder>() {
    var items = mutableListOf<Basket>()
    var itemClickListener: ((Int, Boolean) -> Unit)? = null
    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = ItemCartBinding.inflate(LayoutInflater.from(parent.context))
        view.root.layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        return CartViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemClickListener = itemClickListener
    }
}

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val context: Context,
) :
    RecyclerView.ViewHolder(binding.root) {
    var itemClickListener: ((Int, Boolean) -> Unit)? = null

    fun bind(item: Basket) {
        binding.apply {
            Glide.with(context)
                .load(item.images)
                .placeholder(R.drawable.placeholder_image)
                .into(productImageView)
            amountTextView.text = item.amount.toString()
            productPriceTextView.text = (item.amount!! * item.price).toString() + "$ us"
            productTitleTextView.text = item.title
            moreButton.setOnClickListener {
                itemClickListener?.invoke(adapterPosition, true)
            }

            lessButton.setOnClickListener {
                itemClickListener?.invoke(adapterPosition, false)
            }
        }
    }
}