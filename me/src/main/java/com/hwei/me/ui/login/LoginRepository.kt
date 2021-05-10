package com.hwei.me.ui.login

import com.hwei.lib_common.net.base.Resource
import com.hwei.me.bean.UserBean
import com.hwei.me.net.MeService
import javax.inject.Inject

class LoginRepository @Inject constructor(private val meService: MeService){
    suspend fun login(username: String, password: String): Resource<UserBean> {
        return meService.login(username, password).resource
    }

    suspend fun register(
        username: String,
        password: String,
        repassword: String
    ): Resource<UserBean> {
        return meService
            .register(username, password, repassword).resource
    }
}