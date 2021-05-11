package com.hwei.lib_common.paging

import androidx.paging.PagingConfig

private const val PAGE_SIZE = 20

object PagingConfigFactory {
    fun get() = PagingConfig(pageSize = PAGE_SIZE,initialLoadSize = 20)
}