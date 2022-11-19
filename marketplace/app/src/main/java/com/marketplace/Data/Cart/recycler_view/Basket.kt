package com.marketplace.Data.Cart.recycler_view

data class Basket(
    val id: Int,
    val images: String,
    val price: Int,
    val title: String,
    var amount: Int?
)