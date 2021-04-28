package com.hwei.lib_common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.hwei.lib_common.widge.AppCrashHandler

open class BaseApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context :Context
    }

    override fun onCreate() {
        super.onCreate()
        AppCrashHandler.init(this)
        context = this
    }

}