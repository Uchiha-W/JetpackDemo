package com.hwei.lib_common.net

data class BaseResult<T>(val errorCode:Int,val errorMsg:String,val data:T)