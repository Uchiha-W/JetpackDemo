package com.hwei.lib_common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map

object UserManager {

//    /**
//     * 用户信息
//     */
//    val userBean = object : MutableLiveData<UserBean>(SpStorage.getValue("userBean", null)) {
//        override fun setValue(value: UserBean?) {
//            super.setValue(value)
//            SpStorage.setValue("userBean", value)
//        }
//    }

    val userBean = MutableLiveData<UserBean>()

    /**
     * 登录状态
     */
    val isLogin: LiveData<Boolean>
        get() = userBean.map {
            it == null
        }

}