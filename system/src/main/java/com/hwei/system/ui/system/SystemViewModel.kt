package com.hwei.system.ui.system

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.hwei.lib_base.base.BaseViewModel
import com.hwei.lib_base.net.request
import com.hwei.lib_base.paging.BasePageDataSource
import com.hwei.lib_base.paging.PagingConfigFactory
import com.hwei.system.bean.SystemBeanItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SystemViewModel @Inject constructor(private val systemRepository: SystemRepository) :
    BaseViewModel() {

    private val _systemBeanItem = MutableLiveData<List<SystemBeanItem>>()
    val systemBeanItem: LiveData<List<SystemBeanItem>>
        get() = _systemBeanItem

    var id: Int = 0

    val pager = Pager(PagingConfigFactory.get()) {
        BasePageDataSource {
            systemRepository.getArticleList(it, id).data()
        }
    }.flow.cachedIn(viewModelScope)


    fun getSystemData() {
        request<List<SystemBeanItem>> {
            onRequest {
                systemRepository.getSystemData()
            }
            onSuccess {
                _systemBeanItem.value = it
            }
        }
    }

}