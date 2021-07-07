package com.hwei.lib_common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val showLoadingLiveData = MutableLiveData<Boolean>()
}