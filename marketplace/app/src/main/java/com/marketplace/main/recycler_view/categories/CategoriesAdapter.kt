package com.marketplace.main.recycler_view.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marketplace.R
import com.marketplace.databinding.ItemCategoryBinding

class CategoriesAdapter : RecyclerView.Adapter<CategoriesViewHolder>() {
    var items = mutableListOf<CategoryItem>()
    var itemClickListener: ((Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false), parent.context)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemClickListener = itemClickListener
    }

    override fun getItemCount(): Int = items.size
}

class CategoriesViewHolder(private val binding: ItemCategoryBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {
    var itemClickListener: ((Int) -> Unit)? = null

    fun bind(item: CategoryItem) {
        when (item.id) {
            0 -> { bindItem(item.name, R.drawable.phone_vector, item.isOn) }
            1 -> { bindItem(item.name, R.drawable.pc_vector, item.isOn) }
            2 -> { bindItem(item.name, R.drawable.heart_vector, item.isOn) }
            3 -> { bindItem(item.name, R.drawable.books_vector, item.isOn) }
        }
        binding.root.setOnClickListener {
            itemClickListener?.invoke(adapterPosition)
        }
    }

    private fun bindItem(name: String, imageRes: Int, isOn: Boolean) {
        binding.nameTextView.text = name
        Glide.with(context)
            .load(imageRes)
            .into(binding.vectorAsset)
        if (isOn) {
            binding.background.setCardBackgroundColor(context.resources.getColor(R.color.orange))
            binding.vectorAsset.setColorFilter(context.resources.getColor(R.color.white))
        } else {
            binding.background.setCardBackgroundColor(context.resources.getColor(R.color.white))
            binding.vectorAsset.setColorFilter(context.resources.getColor(R.color.icon_gray))
        }
    }
}

data class CategoryItem(
    val id: Int,
    val name: String,
    var isOn: Boolean,
)