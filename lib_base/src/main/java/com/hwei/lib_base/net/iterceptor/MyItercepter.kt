package com.hwei.lib_base.net.iterceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class MyItercepter : Interceptor {
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