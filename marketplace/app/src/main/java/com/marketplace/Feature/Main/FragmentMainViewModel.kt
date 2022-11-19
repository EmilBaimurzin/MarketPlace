package com.marketplace.Feature.Main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marketplace.Common.networking.SmartphoneDetailsNet
import com.marketplace.Data.Main.FragmentMainRepository
import com.marketplace.Data.Main.recycler_view.best_seller.BestSellerItem
import com.marketplace.Data.Main.recycler_view.categories.CategoryItem
import com.marketplace.Data.Main.recycler_view.hot_sale.HotSaleItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FragmentMainViewModel @Inject constructor(
    private val repository: FragmentMainRepository,
) : ViewModel() {

    private val _categoryList = MutableLiveData<List<CategoryItem>>()
    val categoryList = _categoryList

    private val _hotSaleList = MutableLiveData<List<HotSaleItems>>()
    val hotSaleList = _hotSaleList

    private val _bestSellerList = MutableLiveData<List<BestSellerItem>>()
    val bestSellerList = _bestSellerList

    init {
        viewModelScope.launch(Dispatchers.Default) {
            _categoryList.postValue(repository.getCategories())
            _hotSaleList.postValue(repository.getHotSales())
            _bestSellerList.postValue(repository.getBestSellers())
        }
    }

    fun changeCategory(newList: List<CategoryItem>) {
        _categoryList.postValue(newList)
    }

    fun getSmartphoneDetails(details: (SmartphoneDetailsNet?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            details(repository.getSmartphoneDetails())
        }
    }

    fun getBestSellerPreview(): MutableList<BestSellerItem> {
        return repository.getBestSellerPreview()
    }

    fun getHotSalesPreview(): MutableList<HotSaleItems> {
        return repository.getHotSalesPreview()
    }
}