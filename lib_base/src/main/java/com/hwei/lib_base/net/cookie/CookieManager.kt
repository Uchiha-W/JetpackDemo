package com.hwei.lib_base.net.cookie

import com.hwei.lib_base.database.AppDataBase
import com.hwei.lib_base.database.bean.CookieEntity
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class CookieManager : CookieJar {

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

class CookieStore {

    fun saveCookie(url: HttpUrl, cookie: Cookie) {
        AppDataBase.cookieDao().insert(CookieEntity(url.host, cookie.name, cookie))
    }

    fun getCookie(url: HttpUrl): List<Cookie> {
        return AppDataBase.cookieDao().getAll(url.host)?.map {
            it.cookie
        } ?: emptyList()
    }
}