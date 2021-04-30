package com.hwei.me.net

import com.hwei.lib_common.net.BaseResult
import com.hwei.me.bean.UserBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MeApi {
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
}