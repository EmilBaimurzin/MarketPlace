package com.marketplace.cart

import com.marketplace.Data.Cart.recycler_view.Basket

data class BasketNet(
    val basket: List<Basket>,
    val delivery: String,
    val id: String,
    val total: Int
)