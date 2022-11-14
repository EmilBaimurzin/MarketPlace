package com.marketplace.cart

import android.util.Log
import com.marketplace.cart.recycler_view.Basket
import com.marketplace.networking.Network
import com.marketplace.networking.SmartphoneDetailsNet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CartRepository {
    suspend fun getSmartphoneDetails(): BasketNet? {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.IO) {
                    Network.mainInterface.getCartData().enqueue(object : Callback<BasketNet> {
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