package com.hwei.home.ui

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.hwei.home.bean.BannerBean
import com.hwei.lib_common.net.request
import com.hwei.lib_common.paging.BasePageDataSource
import com.hwei.lib_common.paging.PagingConfigFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    private val _bannerData = MutableLiveData<List<BannerBean>>()
    val bannerData: LiveData<List<BannerBean>>
        get() = _bannerData


    val livePageData = Pager(PagingConfigFactory.get()) {
        BasePageDataSource {
            homeRepository.getArticleList(it)
        }
    }.flow.cachedIn(viewModelScope).asLiveData()

    fun getBannerList() {
        request<List<BannerBean>> {
            onRequest {
                homeRepository.getBannerList()
            }
            onSuccess {
                _bannerData.value = it
            }
        }
    }
}