package com.hwei.system.net

import com.hwei.lib_base.net.base.BasePage
import com.hwei.lib_base.net.base.BaseResult
import com.hwei.system.bean.ArticleItem
import com.hwei.system.bean.SystemBeanItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SystemService {

    @GET("/tree/json")
    suspend fun getSystemData(): BaseResult<List<SystemBeanItem>>

    @GET("/article/list/{page}/json?")
    suspend fun getArticleList(
        @Path("page") page: Int,
        @Query("cid") id: Int,
    ): BaseResult<BasePage<ArticleItem>>
}