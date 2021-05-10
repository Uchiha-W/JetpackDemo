package com.hwei.home.ui

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.hwei.home.bean.BannerBean
import com.hwei.home.net.HomeApi
import com.hwei.home.paging.pageConfig
import com.hwei.lib_common.net.RetrofitManager
import com.hwei.lib_common.net.request
import com.hwei.lib_common.paging.BasePageDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _bannerData = MutableLiveData<List<BannerBean>>()
    val bannerData: LiveData<List<BannerBean>>
        get() = _bannerData

    @Inject
    lateinit var homeApi: HomeApi
    val livePageData = Pager(pageConfig) {
        BasePageDataSource {
            homeApi.getArticleList(it).data
        }
    }.flow.cachedIn(viewModelScope).asLiveData()

    fun getBannerList() {
        request<List<BannerBean>> {
            onRequest {
                homeApi.getBannerList().resource
            }
            onSuccess {
                _bannerData.value = it
            }
        }
    }
}