package com.hwei.lib_common.net

import android.util.Log
import com.hwei.lib_common.BuildConfig
import com.hwei.lib_common.database.AppDataBase
import com.hwei.lib_common.database.bean.CookieEntity
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://www.wanandroid.com"
var RetrofitManager: Retrofit = getRetrofit(getOkhttp())

private fun getOkhttp(): OkHttpClient {
    return OkHttpClient.Builder().callTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                //addNetworkInterceptor(myItercepter())
            }
        }
        .cookieJar(CookieManager())
        .build()
}

private class myItercepter : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val sb = StringBuilder()
        for (i in chain.request().headers) {
            sb.append(i).append("\n")
        }
        val response = chain.proceed(chain.request())
        Log.e("okhttp", sb.toString())
        return response
    }
}

private fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()
}

private class CookieManager : CookieJar {

    private val cookieStore = CookieStore()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore.getCookie(url)
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        for (cookie in cookies) {
            cookieStore.saveCookie(url, cookie)
        }
    }
}

private class CookieStore {

    fun saveCookie(url: HttpUrl, cookie: Cookie) {
        AppDataBase.cookieDao().insert(CookieEntity(url.host, cookie.name, cookie))
    }

    fun getCookie(url: HttpUrl): List<Cookie> {
        return AppDataBase.cookieDao().getAll(url.host)?.map {
            it.cookie
        } ?: emptyList()
    }
}