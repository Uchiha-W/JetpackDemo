package com.hwei.home.net

import androidx.paging.PagedList
import com.hwei.home.bean.Article
import com.hwei.lib_common.database.bean.Concert
import com.hwei.lib_common.net.BaseResult
import retrofit2.http.GET

interface HomeApi {
    @GET("/wxarticle/chapters/json")
    suspend fun getArticleList(): BaseResult<List<Article>>
}