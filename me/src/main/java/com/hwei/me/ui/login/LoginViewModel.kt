package com.hwei.me.ui.login

import com.hwei.lib_base.base.BaseViewModel
import com.hwei.lib_base.net.flowRequest
import com.hwei.lib_common.UserBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    BaseViewModel() {

    fun login(username: String, password: String): Flow<UserBean> {
        return flowRequest {
            onRequest {
                loginRepository.login(username, password)
            }
        }
    }

    fun register(username: String, password: String, repassword: String): Flow<UserBean> {
        return flowRequest {
            onRequest {
                loginRepository.register(username, password, repassword)
            }
        }
    }
}