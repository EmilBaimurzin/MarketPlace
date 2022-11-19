package com.marketplace.Data.Cart

import android.util.Log
import com.marketplace.Common.networking.NetworkInterface
import com.marketplace.Data.Cart.recycler_view.Basket
import com.marketplace.cart.BasketNet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CartRepository @Inject constructor(
    private val networkInterface: NetworkInterface,
) {

    suspend fun getSmartphoneDetails(): List<Basket> {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.Unconfined).launch {
                getCartData()?.basket?.map {
                    Basket(
                        it.id,
                        it.images,
                        it.price,
                        it.title,
                        1
                    )
                }?.let { continuation.resume(it) } ?: continuation.resume(emptyList())
            }
        }
    }

    suspend fun getCartData(): BasketNet? {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.IO) {
                    networkInterface.getCartData().enqueue(object : Callback<BasketNet> {
                        override fun onResponse(
                            call: Call<BasketNet>,
                            response: Response<BasketNet>,
                        ) {
                            if (response.isSuccessful) {
                                val basket = response.body()
                                continuation.resume(basket)
                            } else {
                                continuation.resume(null)
                            }
                        }

                        override fun onFailure(call: Call<BasketNet>, t: Throwable) {
                            Log.e("error", "unable to get products", t)
                            continuation.resume(null)
                        }
                    })
                }
            }
        }
    }
}