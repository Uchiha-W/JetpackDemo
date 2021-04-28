package com.hwei.home.bean

import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import javax.xml.datatype.DatatypeFactory

//class MyDataSource : PageKeyedDataSource<Int,Article>() {
//    override fun loadInitial(
//        params: LoadInitialParams<Int>,
//        callback: LoadInitialCallback<Int, Article>
//    ) {
//
//    }
//
//    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
//
//    }
//
//    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
//
//    }
//}
//
//class MyDataSourceFactory: DataSource.Factory<Int, Article>() {
//    override fun create(): DataSource<Int, Article> {
//        return MyDataSource()
//    }
//}

val pageListConfig = PagedList.Config.Builder()
    .setPageSize(20)
    .setEnablePlaceholders(true)
    .setInitialLoadSizeHint(10)
    .setMaxSize(Int.MAX_VALUE)
    .setPrefetchDistance(5)
    .build()

//val LivePageData = LivePagedListBuilder(MyDataSourceFactory(), pageListConfig).build()
