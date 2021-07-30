package com.hwei.lib_base.net.base

data class BaseResult<T>(val errorCode: Int, val errorMsg: String, val data: T) {
    val resource: Resource<T>
        get() = if (errorCode == 0) {
            Resource.Success(data)
        } else {
            Resource.Error(errorMsg)
        }
}
