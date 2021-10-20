package com.hwei.me.ui.login

import com.hwei.lib_base.base.BaseViewModel
import com.hwei.lib_base.net.flowRequest
import com.hwei.lib_common.UserBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    BaseViewModel() {

    val stateFlow = MutableStateFlow(UserBean.emptyUser())

    val sharedFlow = MutableSharedFlow<UserBean>()

    fun login(username: String, password: String): Flow<UserBean> {
        return flowRequest {
            onRequest {
                loginRepository.login(username, password).apply {
                    stateFlow.emit(this.data())
                    sharedFlow.emit(this.data())
                }
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