package com.hwei.lib_base.net

import androidx.lifecycle.viewModelScope
import com.hwei.lib_base.base.BaseViewModel
import com.hwei.lib_base.ktx.showToast
import com.hwei.lib_base.net.base.Resource
import kotlinx.coroutines.*

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

 fun <T> BaseViewModel.request(
    showLoading: Boolean = false,
    block: RequestDSL<T>.() -> Unit
) {
    object : RequestDSL<T>() {
        override fun build() {
            viewModelScope.launch(Dispatchers.Main + CoroutineExceptionHandler { _, throwable ->
                showToast(throwable.toString())
            }) {
                ensureActive()
                try {
                    if (showLoading) {
                        showLoadingLiveData.value = true
                    }
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
                        onFailure?.invoke(e.message.orEmpty())
                        showToast(e.message.orEmpty())
                    }
                } finally {
                    onComplete?.invoke()
                    if (showLoading) {
                        showLoadingLiveData.value = false
                    }
                }
            }

        }
    }.apply(block).build()
}

inline fun <T> BaseViewModel.pollRequest(
    delayMs: Long = 1000,
    crossinline block: RequestDSL<T>.() -> Unit
) {
    object : RequestDSL<T>() {
        override fun build() {
            viewModelScope.launch(Dispatchers.Main + CoroutineExceptionHandler { _, _ ->

            }) {
                while (true) {
                    delay(delayMs)
                    ensureActive()
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
                            onFailure?.invoke(e.message.orEmpty())
                            showToast(e.message.orEmpty())
                        }
                    } finally {
                        onComplete?.invoke()
                    }
                }
            }
        }
    }.apply(block).build()
}

