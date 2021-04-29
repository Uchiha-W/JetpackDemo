package com.hwei.lib_common.net

data class BaseResult<T>(val errorCode:Int,val errorMsg:String,val data:T)

data class BasePage<T>(val curPage:Int,val offset:Int,val over:Boolean,val pageCount:Int,val size:Int,val total:Int,val datas: T)