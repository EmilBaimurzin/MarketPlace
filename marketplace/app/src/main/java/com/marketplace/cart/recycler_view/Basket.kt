package com.marketplace.cart.recycler_view

data class Basket(
    val id: Int,
    val images: String,
    val price: Int,
    val title: String,
    var amount: Int?
)