package com.hwei.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.hwei.home.bean.Article
import com.hwei.home.net.HomeApi
import com.hwei.lib_common.net.RetrofitManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val _article = MutableLiveData<List<Article>>()
    val article: LiveData<List<Article>>
        get() = _article
    var livePageData: LiveData<PagedList<Article>>

    init {
        val factory = MyDataSourceFactory(viewModelScope)
        val pageListConfig = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setMaxSize(Int.MAX_VALUE)
            .setPrefetchDistance(5)
            .build()
        livePageData = LivePagedListBuilder<Int, Article>(factory, pageListConfig).build()
    }

    class MyDataSourceFactory(val scope: CoroutineScope) : DataSource.Factory<Int, Article>() {

        override fun create(): DataSource<Int, Article> {
            return MyDataSource(scope)
        }
    }

    class MyDataSource(val scope: CoroutineScope) : PageKeyedDataSource<Int, Article>() {
        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, Article>
        ) {
            scope.launch {
                try {
                    val result = withContext(Dispatchers.Default) {
                        RetrofitManager.create(HomeApi::class.java).getArticleList().data
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

        }

    }
}