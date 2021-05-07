package com.hwei.me.me

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hwei.lib_common.base.BaseViewModel
import com.hwei.lib_common.ktx.showToast
import com.hwei.lib_common.net.Resource
import com.hwei.lib_common.net.RetrofitManager
import com.hwei.lib_common.net.request
import com.hwei.me.bean.UserBean
import com.hwei.me.net.MeApi
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    private val _userBean = MutableLiveData<UserBean>()
    val userBean: LiveData<UserBean>
        get() = _userBean
    private val loginRepository = LoginRepository()
    fun login(username: String, password: String) {
        request<UserBean> {
            onRequest {
                loginRepository.login(username, password)
            }
            onSuccess{
                _userBean.value = it
            }

        }
    }

    fun register(username: String, password: String, repassword: String) {
        request<UserBean> {
            onRequest {
                loginRepository.register(username, password, repassword)
            }
            onSuccess {
                _userBean.value = it
            }
        }
    }
}

data class Loading(var load: Boolean)