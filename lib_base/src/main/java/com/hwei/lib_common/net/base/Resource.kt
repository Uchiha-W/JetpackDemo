package com.hwei.lib_common.net.base

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String) : Resource<T>(message = message)


    fun error(): String {
        return (this as Error).message ?: ""
    }

    fun data(): T {
        return (this as Success).data ?: Any() as T
    }
}