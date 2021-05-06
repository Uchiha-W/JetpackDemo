package com.hwei.lib_common.database.bean

import androidx.room.Entity
import okhttp3.Cookie

@Entity(primaryKeys = ["host", "cookieName"])
data class CookieEntity(
    val host: String,
    val cookieName: String,
    val cookie: Cookie
)