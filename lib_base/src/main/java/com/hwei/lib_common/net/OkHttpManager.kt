package com.hwei.lib_common.net

import com.hwei.lib_common.BuildConfig
import com.hwei.lib_common.sp.SpDelegate
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
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
            }
        }
        .cookieJar(CookieManager())
        .build()
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

    private var sp by SpDelegate<HashMap<String, Cookie>>("Cookie", HashMap())

    fun saveCookie(url: HttpUrl, cookie: Cookie) {
        sp[url.host + cookie.name] = cookie
    }

    fun getCookie(url: HttpUrl): List<Cookie> {
        val list = sp.filter {
            it.key.contains(url.host)
        }.values.toMutableList()
        return list
    }
}