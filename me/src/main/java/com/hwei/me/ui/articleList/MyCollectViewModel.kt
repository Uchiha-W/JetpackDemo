package com.hwei.me.ui.articleList

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.hwei.lib_base.base.BaseViewModel
import com.hwei.lib_base.paging.BasePageDataSource
import com.hwei.lib_base.paging.PagingConfigFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyCollectViewModel @Inject constructor(private val myCollectRepository: MyCollectRepository) :
    BaseViewModel() {


    val pager = Pager(PagingConfigFactory.get()) {
        BasePageDataSource {
            myCollectRepository.getMyCollectList(it).data()
        }
    }.flow.cachedIn(viewModelScope)

}