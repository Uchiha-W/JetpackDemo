package com.hwei.lib_common.net

data class BaseResult<T>(val errorCode: Int, val errorMsg: String, val data: T) {
    val resource: Resource<T>
        get() = if (errorCode == 200) {
            Resource.Success(data)
        } else {
            Resource.Error(errorMsg)
        }
}

data class BasePage<T>(
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: T
)

