package com.hwei.home.ui

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.hwei.home.bean.BannerBean
import com.hwei.lib_base.base.BaseViewModel
import com.hwei.lib_base.net.request
import com.hwei.lib_base.paging.BasePageDataSource
import com.hwei.lib_base.paging.PagingConfigFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : BaseViewModel() {

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