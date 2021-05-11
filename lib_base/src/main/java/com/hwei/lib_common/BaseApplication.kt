package com.hwei.lib_common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.hwei.lib_common.widge.AppCrashHandler
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout


open class BaseApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        AppCrashHandler.init(this)
        context = this
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { _, layout ->
            layout.setPrimaryColorsId(
                R.color.app_300,
                android.R.color.white
            )
            ClassicsHeader(context)
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }

    }

}