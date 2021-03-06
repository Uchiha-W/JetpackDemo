package com.hwei.lib_base.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hwei.lib_base.net.base.BasePage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BasePageDataSource<V : Any>(private val block: suspend (Int) -> BasePage<V>) :
    PagingSource<Int, V>() {
    override fun getRefreshKey(state: PagingState<Int, V>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> {
        return try {
            val result = withContext(Dispatchers.IO) {
                block.invoke(params.key ?: 0)
            }
            if (result.over) {
                LoadResult.Page(result.datas, null, null)
            } else {
                LoadResult.Page(result.datas, null, result.curPage)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}