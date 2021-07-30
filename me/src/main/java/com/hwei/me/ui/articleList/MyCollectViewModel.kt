package com.hwei.me.ui.articleList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hwei.lib_base.base.BaseViewModel
import com.hwei.lib_base.net.base.BasePage
import com.hwei.lib_base.net.request
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@HiltViewModel
class MyCollectViewModel @Inject constructor(private val myCollectRepository: MyCollectRepository) :
    BaseViewModel() {

    private val _collectList = MutableLiveData<BasePage<String>>()
    val collectList: LiveData<BasePage<String>>
        get() = _collectList

    fun getCollectList(page: Int) {
        request<BasePage<String>> {
            onRequest {
                myCollectRepository.getMyCollectList(page)
            }
            onSuccess {
                _collectList.value = it
            }
        }
    }

}