package com.hwei.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import com.hwei.home.bean.BannerBean
import com.hwei.home.net.HomeApi
import com.hwei.home.paging.HomeDataSource.HomePageSourceFactory
import com.hwei.home.paging.pageListConfig
import com.hwei.lib_common.net.RetrofitManager
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _bannerData = MutableLiveData<List<BannerBean>>()
    val bannerData: LiveData<List<BannerBean>>
        get() = _bannerData

    val livePageData =
        LivePagedListBuilder(HomePageSourceFactory(viewModelScope), pageListConfig).build()

    fun getBannerList() {
        viewModelScope.launch {
            val result = RetrofitManager.create(HomeApi::class.java).getBannerList().data
            _bannerData.value = result
        }
    }
}