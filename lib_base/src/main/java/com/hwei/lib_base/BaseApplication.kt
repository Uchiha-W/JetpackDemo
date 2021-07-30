package com.hwei.lib_base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.hwei.lib_base.widge.AppCrashHandler
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV


open class BaseApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        AppCrashHandler.init(this)
        MMKV.initialize(this).apply {
            if (BuildConfig.DEBUG) {
                Log.d("MMKV root:", this)
            }
        }
        val userStrategy = CrashReport.UserStrategy(this).apply {
            appVersion = context.packageManager.getPackageInfo(packageName, 0).versionName
        }
        if (BuildConfig.DEBUG) {
            CrashReport.initCrashReport(this, "b13204d10b", true, userStrategy)
        } else {
            CrashReport.initCrashReport(this, "b13204d10b", false, userStrategy)
        }

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