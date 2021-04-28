package com.hwei.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import com.hwei.home.bean.Article
import com.hwei.home.bean.pageListConfig
import com.hwei.home.net.HomeApi
import com.hwei.lib_common.net.RetrofitManager
import kotlinx.coroutines.*
import java.lang.Exception

class HomeViewModel : ViewModel() {

    private val _article = MutableLiveData<List<Article>>()
    val article: LiveData<List<Article>>
        get() = _article
    val factory = MyDataSourceFactory()
    val LivePageData = LivePagedListBuilder(factory, pageListConfig).build()
    class MyDataSourceFactory : DataSource.Factory<Int, Article>() {
        val dataSource = MyDataSource(GlobalScope)
        override fun create(): DataSource<Int, Article> {
            return dataSource
        }
    }
    class MyDataSource(val scope: CoroutineScope) : PageKeyedDataSource<Int, Article>() {
        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, Article>
        ) {
            scope.launch{
                Log.e("tag", "initData")
                try {
                    val data = RetrofitManager.create(HomeApi::class.java).getArticleList().data
                        callback.onResult(
                            data,
                            null,
                            null
                        )
                }catch (e:Exception){
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