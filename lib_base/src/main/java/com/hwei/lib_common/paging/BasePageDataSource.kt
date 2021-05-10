package com.hwei.lib_common.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hwei.lib_common.net.base.BasePage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BasePageDataSource<V : Any>(private val block: suspend (Int) -> BasePage<V>) :
    PagingSource<Int, V>() {
    override fun getRefreshKey(state: PagingState<Int, V>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> {
        return try {
            val result = withContext(Dispatchers.Default) {
                block.invoke(params.key ?: 0)
            }
            if (result.over) {
                LoadResult.Page(result.datas, null, null)
            } else {
                LoadResult.Page(result.datas, null, result.curPage)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}