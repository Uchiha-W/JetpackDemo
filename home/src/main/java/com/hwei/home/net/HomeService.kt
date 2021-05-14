package com.hwei.home.net

import com.hwei.home.bean.Article
import com.hwei.home.bean.BannerBean
import com.hwei.lib_common.net.base.BasePage
import com.hwei.lib_common.net.base.BaseResult
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {
    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): BaseResult<BasePage<Article>>

    @GET("/banner/json")
    suspend fun getBannerList(): BaseResult<List<BannerBean>>
}