package com.hwei.me.net

import com.hwei.lib_base.net.base.BasePage
import com.hwei.lib_base.net.base.BaseResult
import com.hwei.lib_common.UserBean
import retrofit2.http.*

interface MeService {
    @POST("/user/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): BaseResult<UserBean>

    @POST("/user/register")
    @FormUrlEncoded
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): BaseResult<UserBean>

    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollectList(@Path("page") page: Int): BaseResult<BasePage<String>>
}