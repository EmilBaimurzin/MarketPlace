package com.marketplace.Data.Main

import android.util.Log
import com.marketplace.Data.Main.recycler_view.best_seller.BestSellerItem
import com.marketplace.Data.Main.recycler_view.categories.CategoryItem
import com.marketplace.Data.Main.recycler_view.hot_sale.HotSaleItems
import com.marketplace.Common.networking.Network
import com.marketplace.Common.networking.NetworkInterface
import com.marketplace.Common.networking.Products
import com.marketplace.Common.networking.SmartphoneDetailsNet
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

class FragmentMainRepository @Inject constructor(
    val networkInterface: NetworkInterface
) {
    fun getCategories(): List<CategoryItem> {
        return listOf(
            CategoryItem(0, "Phones", true),
            CategoryItem(1, "Computers", false),
            CategoryItem(2, "Health", false),
            CategoryItem(3, "Books", false),
        )
    }

    suspend fun getHotSales(): List<HotSaleItems> {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.IO) {
                    networkInterface.getAllProducts().enqueue(object : Callback<Products> {
                        override fun onResponse(
                            call: Call<Products>,
                            response: Response<Products>,
                        ) {
                            if (response.isSuccessful) {
                                val unConvertedList = response.body()
                                continuation.resume(unConvertedList!!.convertToHotSaleList())
                            } else {
                                continuation.resume(emptyList())
                            }
                        }

                        override fun onFailure(call: Call<Products>, t: Throwable) {
                            Log.e("error", "unable to get products", t)
                            continuation.resume(emptyList())
                        }
                    })
                }
            }
        }
    }

    suspend fun getBestSellers(): List<BestSellerItem> {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.IO) {
                    networkInterface.getAllProducts().enqueue(object : Callback<Products> {
                        override fun onResponse(
                            call: Call<Products>,
                            response: Response<Products>,
                        ) {
                            if (response.isSuccessful) {
                                val unConvertedList = response.body()
                                continuation.resume(unConvertedList!!.convertToBestSellerList())
                            } else {
                                continuation.resume(emptyList())
                            }
                        }

                        override fun onFailure(call: Call<Products>, t: Throwable) {
                            Log.e("error", "unable to get products", t)
                            continuation.resume(emptyList())
                        }
                    })
                }
            }
        }
    }

    suspend fun getSmartphoneDetails(): SmartphoneDetailsNet? {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.IO) {
                    networkInterface.getSmartphoneDetails()
                        .enqueue(object : Callback<SmartphoneDetailsNet> {
                            override fun onResponse(
                                call: Call<SmartphoneDetailsNet>,
                                response: Response<SmartphoneDetailsNet>,
                            ) {
                                if (response.isSuccessful) {
                                    val smartphoneDetails = response.body()
                                    continuation.resume(smartphoneDetails)
                                } else {
                                    continuation.resume(null)
                                }
                            }

                            override fun onFailure(call: Call<SmartphoneDetailsNet>, t: Throwable) {
                                Log.e("error", "unable to get products", t)
                                continuation.resume(null)
                            }
                        })
                }
            }
        }
    }

    fun getHotSalesPreview(): MutableList<HotSaleItems> {
        return mutableListOf(HotSaleItems(
            isNew = false,
            title = "",
            description = "",
            image = "",
            isPreview = true))
    }

    fun getBestSellerPreview(): MutableList<BestSellerItem> {
        val list = mutableListOf<BestSellerItem>()
        repeat(4) {
            list.add(BestSellerItem(title = "",
                discountPrice = 0,
                priceWithoutDiscount = 0,
                isFavorites = false,
                picture = "",
                isPreview = true))
        }
        return list
    }
}