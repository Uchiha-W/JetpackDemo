package com.hwei.lib_common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.hwei.lib_base.sp.Sp

object UserManager {

    /**
     * 用户信息
     */
    val userBean =
        object : MutableLiveData<UserBean>(Sp.getValue("userBean", UserBean.emptyUser())) {
            override fun postValue(value: UserBean?) {
                super.postValue(value)
                Sp.setValue("userBean", value)
            }

            override fun setValue(value: UserBean?) {
                super.setValue(value)
                Sp.setValue("userBean", value)
            }
        }

    /**
     * 登录状态
     */
    val isLogin: LiveData<Boolean>
        get() = userBean.map {
            !UserBean.isEmpty(it)
        }
}