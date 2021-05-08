package com.hwei.home.paging.DataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hwei.home.bean.Article
import com.hwei.home.net.HomeApi
import com.hwei.lib_common.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class HomePageDataSource : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val result = withContext(Dispatchers.Default) {
                RetrofitManager.create(HomeApi::class.java)
                    .getArticleList(params.key ?: 0).data
            }
            if (result.over) {
                LoadResult.Page(result.datas, null, null)
            } else {
                LoadResult.Page(result.datas, null, result.curPage + 1)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}