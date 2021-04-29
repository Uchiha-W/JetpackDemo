package com.hwei.home.paging

import androidx.paging.PagedList

val PAGE_SIZE = 20
val pageListConfig = PagedList.Config.Builder()
    .setPageSize(PAGE_SIZE)
    .setEnablePlaceholders(false)
    .setInitialLoadSizeHint(PAGE_SIZE)
    .setMaxSize(Int.MAX_VALUE)
    .setPrefetchDistance(5)
    .build()