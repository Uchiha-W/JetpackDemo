package com.hwei.lib_base.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val showLoadingLiveData = MutableLiveData<Boolean>()
}