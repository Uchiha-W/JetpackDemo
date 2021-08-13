package com.hwei.lib_base.net

import com.hwei.lib_base.base.BaseViewModel
import com.hwei.lib_base.ktx.showToast
import com.hwei.lib_base.net.base.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CancellationException


class FlowRequestDSL<T> {
    var onSuccess: (suspend (T) -> Unit)? = null
    var onFailure: (suspend (errorMsg: String) -> Unit)? = null
    var onRequest: (suspend () -> Resource<T>)? = null
    var onComplete: (suspend () -> Unit)? = null
    fun onSuccess(block: suspend (T) -> Unit) {
        this.onSuccess = block
    }

    fun onFailure(block: suspend (errorMsg: String) -> Unit) {
        this.onFailure = block
    }

    fun onRequest(block: suspend () -> Resource<T>) {
        this.onRequest = block
    }

    fun onComplete(block: suspend () -> Unit) {
        this.onComplete = block
    }
}

fun <T> BaseViewModel.flowRequest(
    showLoading: Boolean = false,
    block: FlowRequestDSL<T>.() -> Unit
): Flow<T> {
    return flow<T> {
        val request = FlowRequestDSL<T>().apply(block)
        try {
            if (showLoading) {
                showLoadingLiveData.value = true
            }
            request.onRequest?.invoke().apply {
                when (this) {
                    is Resource.Success -> {
                        request.onSuccess?.invoke(this.data())
                        emit(this.data())
                    }
                    is Resource.Error -> {
                        request.onFailure?.invoke(this.error())
                        showToast(this.error())
                    }
                }
            }
        } catch (e: Exception) {
            if (e !is CancellationException) {
                request.onFailure?.invoke(e.message.orEmpty())
                showToast(e.message.orEmpty())
            }
        } finally {
            request.onComplete?.invoke()
            if (showLoading) {
                showLoadingLiveData.value = false
            }
        }
    }
}