package com.hwei.home.ui

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.hwei.home.bean.BannerBean
import com.hwei.lib_base.base.BaseViewModel
import com.hwei.lib_base.net.flowRequest
import com.hwei.lib_base.paging.BasePageDataSource
import com.hwei.lib_base.paging.PagingConfigFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    BaseViewModel() {

    val livePageData = Pager(PagingConfigFactory.get()) {
        BasePageDataSource {
            homeRepository.getArticleList(it)
        }
    }.flow.cachedIn(viewModelScope).asLiveData()

    fun getBannerList(): Flow<List<BannerBean>> {
        return flowRequest {
            onRequest {
                homeRepository.getBannerList()
            }
        }
    }
}