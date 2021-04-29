package com.hwei.home.net

import com.hwei.home.bean.Article
import com.hwei.home.bean.BannerBean
import com.hwei.lib_common.net.BasePage
import com.hwei.lib_common.net.BaseResult
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeApi {
    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): BaseResult<BasePage<List<Article>>>

    @GET("/banner/json")
    suspend fun getBannerList(): BaseResult<List<BannerBean>>
}