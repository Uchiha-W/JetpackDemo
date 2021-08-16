package com.hwei.home.net

import com.hwei.home.bean.Article
import com.hwei.home.bean.BannerBean
import com.hwei.lib_base.net.base.BasePage
import com.hwei.lib_base.net.base.BaseResult
import retrofit2.http.*

interface HomeService {
    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): BaseResult<BasePage<Article>>

    @GET("/banner/json")
    suspend fun getBannerList(): BaseResult<List<BannerBean>>

    /**
     * 收藏站内文章
     */
    @POST("/lg/collect/{id}/json")
    suspend fun collectInnerArticle(@Path("id") id: Int): BaseResult<Any>

    /**
     * 收藏站外文章
     */
    @POST("/lg/collect/add/json")
    @FormUrlEncoded
    suspend fun collectOuterArticle(
        @Field("title") title: String,
        @Field("author") author: String,
        @Field("link") link: String
    ): BaseResult<Any>
}