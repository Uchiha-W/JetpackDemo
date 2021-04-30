package com.hwei.home.paging.DataSource

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.hwei.home.bean.Article
import com.hwei.home.net.HomeApi
import com.hwei.lib_common.net.RetrofitManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePageSourceFactory(private val scope: CoroutineScope) :
    DataSource.Factory<Int, Article>() {
    override fun create(): DataSource<Int, Article> {
        return HomePageDataSource(scope)
    }
}

class HomePageDataSource(private val scope: CoroutineScope) : PageKeyedDataSource<Int, Article>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        scope.launch {
            try {
                val result = withContext(Dispatchers.Default) {
                    RetrofitManager.create(HomeApi::class.java).getArticleList(0).data.datas
                }
                callback.onResult(result, null, 2)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        scope.launch {
            try {
                val result = withContext(Dispatchers.Default) {
                    RetrofitManager.create(HomeApi::class.java)
                        .getArticleList(params.key - 1).data
                }
                if (result.over) {
                    callback.onResult(result.datas, null)
                } else {
                    callback.onResult(result.datas, params.key + 1)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}