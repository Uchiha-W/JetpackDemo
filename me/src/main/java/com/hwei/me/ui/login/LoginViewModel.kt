package com.hwei.me.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hwei.lib_base.base.BaseViewModel
import com.hwei.lib_base.net.request
import com.hwei.lib_common.UserBean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    BaseViewModel() {

    private val _loginUserBean = MutableLiveData<UserBean>()
    val loginUserBean: LiveData<UserBean>
        get() = _loginUserBean

    private val _registerUserBean = MutableLiveData<UserBean>()
    val registerUserBean: LiveData<UserBean>
        get() = _registerUserBean

    fun login(username: String, password: String) {
        request<UserBean> {
            onRequest {
                loginRepository.login(username, password)
            }
            onSuccess {
                _loginUserBean.value = it
            }

        }
    }

    fun register(username: String, password: String, repassword: String) {
        request<UserBean> {
            onRequest {
                loginRepository.register(username, password, repassword)
            }
            onSuccess {
                _registerUserBean.value = it
            }
        }
    }
}