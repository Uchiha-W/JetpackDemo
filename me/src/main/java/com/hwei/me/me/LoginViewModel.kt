package com.hwei.me.me

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwei.lib_common.net.RetrofitManager
import com.hwei.me.net.MeApi
import kotlinx.coroutines.launch

class LoginViewModel:ViewModel() {


    fun login(username: String, password: String){
        viewModelScope.launch {
            val result = RetrofitManager.create(MeApi::class.java).login(username, password)
        }
    }
    fun register(username: String,password: String,repassword:String){
        viewModelScope.launch {
            val result = RetrofitManager.create(MeApi::class.java).register(username, password,repassword)
        }
    }
}