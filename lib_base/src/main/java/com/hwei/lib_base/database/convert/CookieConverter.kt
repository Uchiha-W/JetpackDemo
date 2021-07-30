package com.hwei.lib_base.database.convert

import androidx.room.TypeConverter
import com.google.gson.Gson
import okhttp3.Cookie

class CookieConverter {
    @TypeConverter
    fun cookieToString(cookie: Cookie?): String? {
        return Gson().toJson(cookie, Cookie::class.java)
    }

    @TypeConverter
    fun stringToCookie(string: String?): Cookie {
        return Gson().fromJson(string, Cookie::class.java)
    }
}