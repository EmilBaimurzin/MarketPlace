package com.marketplace.networking

import com.marketplace.cart.BasketNet
import retrofit2.Call
import retrofit2.http.GET

interface NetworkInterface {

    @GET("/v3/654bd15e-b121-49ba-a588-960956b15175")
    fun getAllProducts(): Call<Products>

    @GET("/v3/6c14c560-15c6-4248-b9d2-b4508df7d4f5")
    fun getSmartphoneDetails(): Call<SmartphoneDetailsNet>

    @GET("/v3/53539a72-3c5f-4f30-bbb1-6ca10d42c149")
    fun getCartData(): Call<BasketNet>
}