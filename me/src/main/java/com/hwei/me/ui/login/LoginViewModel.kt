package com.hwei.me.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hwei.lib_common.base.BaseViewModel
import com.hwei.lib_common.net.request
import com.hwei.me.bean.UserBean
import javax.inject.Inject

class  LoginViewModel @Inject constructor(private val loginRepository: LoginRepository): BaseViewModel() {

    private val _userBean = MutableLiveData<UserBean>()
    val userBean: LiveData<UserBean>
        get() = _userBean
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