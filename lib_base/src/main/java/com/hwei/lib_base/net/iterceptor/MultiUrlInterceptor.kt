package com.hwei.lib_base.net.iterceptor

import com.hwei.lib_base.hilt.Module.Companion.DOMAIN_NAME
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 用于多域名时替换域名，like this：
 *
 * const val domain = "www.baidu.com"
 *
 * @GET("/article/list/{page}/json")
 * @Headers("${DOMAIN_HEADER}${domain}")
 * suspend fun getArticleList(@Path("page") page: Int): BaseResult<BasePage<Article>>
 */

class MultiUrlInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        request.headers[DOMAIN_NAME]?.let {
            val newUrl = request.url.newBuilder().host(it).build()
            val newRequest = request.newBuilder().url(newUrl).removeHeader(DOMAIN_NAME).build()
            return chain.proceed(newRequest)
        }
        return chain.proceed(request)
    }
}