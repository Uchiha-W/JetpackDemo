package com.hwei.home.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hwei.lib_base.base.BaseViewModel
import com.hwei.lib_base.net.request
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val articleRepository: ArticleRepository) :
    BaseViewModel() {

    private val _collect = MutableLiveData<Any>()
    val collect: LiveData<Any>
        get() = _collect

    fun collect(id: Int) {
        request<Any> {
            onRequest {
                articleRepository.collect(id)
            }
            onSuccess {
                _collect.value = it
            }
        }
    }
}