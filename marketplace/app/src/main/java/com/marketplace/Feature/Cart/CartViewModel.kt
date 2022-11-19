package com.marketplace.Feature.Cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marketplace.Data.Cart.CartRepository
import com.marketplace.Data.Cart.recycler_view.Basket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
    ) : ViewModel() {
    private val _list = MutableLiveData<List<Basket>>()
    val list: LiveData<List<Basket>> = _list
    var delivery = ""
    var totalPrice = 0

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getCartData()
            val list = repository.getSmartphoneDetails()
            _list.postValue(list)
            delivery = response?.delivery ?: ""
            totalPrice = response?.total ?: 0
        }
    }

    fun add(position: Int) {
        val listItems = _list.value!!
        val itemAmount = listItems[position].amount!! + 1
        listItems[position].amount = itemAmount
        _list.postValue(listItems)

        totalPrice += listItems[position].price
    }

    fun remove(position: Int) {
        val listItems = _list.value
        val itemAmount = listItems?.get(position)!!.amount!! - 1
        if (itemAmount != 0) {
            listItems[position].amount = itemAmount
            _list.postValue(listItems!!)
            var total = 0

            listItems.forEach {
                total += it.price * it.amount!!
            }
            totalPrice = total
        }
    }
}