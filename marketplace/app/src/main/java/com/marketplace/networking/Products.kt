package com.marketplace.networking

import com.marketplace.main.recycler_view.best_seller.BestSellerItem
import com.marketplace.main.recycler_view.hot_sale.HotSaleItems

data class Products(
    val home_store: List<HomeStore>,
    val best_seller: List<BestSellerNet>,
) {
    fun convertToHotSaleList(): List<HotSaleItems> {
        return home_store.map {
            HotSaleItems(
                isNew = it.is_new,
                title = it.title,
                description = it.subtitle,
                image = it.picture
            )
        }
    }

    fun convertToBestSellerList(): List<BestSellerItem> {
        return best_seller.map {
            BestSellerItem(
                title = it.title,
                discountPrice = it.discount_price,
                priceWithoutDiscount = it.price_without_discount,
                isFavorites = it.is_favorites,
                picture = it.picture)
        }
    }
}

data class BestSellerNet(
    val title: String,
    val is_favorites: Boolean,
    val price_without_discount: Int,
    val discount_price: Int,
    val picture: String
)

data class HomeStore(
    val title: String,
    val subtitle: String,
    val picture: String,
    val is_new: Boolean,
)
