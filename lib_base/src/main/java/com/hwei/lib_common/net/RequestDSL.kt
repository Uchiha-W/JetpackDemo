package com.hwei.lib_common.net

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwei.lib_common.ktx.showToast
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class RequestDSL<T> {
    protected var onSuccess: (suspend (T) -> Unit)? = null
    protected var onFailure: (suspend (errorMsg: String) -> Unit)? = null
    protected var onRequest: (suspend CoroutineScope.() -> Resource<T>)? = null
    protected var onComplete: (suspend () -> Unit)? = null
    fun onSuccess(block: suspend (T) -> Unit) {
        this.onSuccess = block
    }

    fun onFailure(block: suspend (errorMsg: String) -> Unit) {
        this.onFailure = block
    }

    fun onRequest(block: suspend CoroutineScope.() -> Resource<T>) {
        this.onRequest = block
    }

    fun onComplete(block: suspend () -> Unit) {
        this.onComplete = block
    }

    abstract fun build()
}

fun <T> ViewModel.request(block: RequestDSL<T>.() -> Unit) {
    return object : RequestDSL<T>() {
        override fun build() {
            viewModelScope.launch {
                try {
                    onRequest?.invoke(this).apply {
                        when (this) {
                            is Resource.Success -> onSuccess?.invoke(this.data())
                            is Resource.Error -> {
                                onFailure?.invoke(this.error())
                                showToast(this.error())
                            }
                        }
                    }
                } catch (e: Exception) {
                    if (e !is CancellationException) {
                        onFailure?.invoke(e.message!!)
                    } else {
                        showToast("exception error")
                    }
                } finally {
                    onComplete?.invoke()
                }
            }
        }
    }.apply(block).build()
}

