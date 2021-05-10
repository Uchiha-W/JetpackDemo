package com.hwei.home.ui

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.hwei.home.bean.BannerBean
import com.hwei.home.net.HomeApi
import com.hwei.home.paging.DataSource.HomePageDataSource
import com.hwei.home.paging.pageConfig
import com.hwei.lib_common.net.RetrofitManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(): ViewModel() {

    private val _bannerData = MutableLiveData<List<BannerBean>>()
    val bannerData: LiveData<List<BannerBean>>
        get() = _bannerData

    val livePageData = Pager(pageConfig) {
        HomePageDataSource()
    }.flow.cachedIn(viewModelScope).asLiveData()

    fun getBannerList() {
        viewModelScope.launch {
            val result = RetrofitManager.create(HomeApi::class.java).getBannerList().data
            _bannerData.value = result
        }
    }
}