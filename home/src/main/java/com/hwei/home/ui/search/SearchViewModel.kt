package com.hwei.home.ui.search

import androidx.paging.Pager
import androidx.paging.PagingData
import com.hwei.home.bean.Article
import com.hwei.lib_base.base.BaseViewModel
import com.hwei.lib_base.paging.BasePageDataSource
import com.hwei.lib_base.paging.PagingConfigFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    BaseViewModel() {

    fun search(content: String): Flow<PagingData<Article>> {
        return Pager(PagingConfigFactory.get()) {
            BasePageDataSource {
                searchRepository.search(it, content).data()
            }
        }.flow
    }

}