package com.hwei.me.me

import com.hwei.lib_common.net.Resource
import com.hwei.lib_common.net.RetrofitManager
import com.hwei.me.bean.UserBean
import com.hwei.me.net.MeApi

class LoginRepository {
    suspend fun login(username: String, password: String): Resource<UserBean> {
        return RetrofitManager.create(MeApi::class.java).login(username, password).resource
    }

    suspend fun register(
        username: String,
        password: String,
        repassword: String
    ): Resource<UserBean> {
        return RetrofitManager.create(MeApi::class.java)
            .register(username, password, repassword).resource
    }
}