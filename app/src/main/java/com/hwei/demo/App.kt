package com.hwei.demo

import android.provider.Settings
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.hwei.lib_base.BaseApplication
import com.hwei.lib_base.util.ScreenAdapterUtil
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class App : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }
        ScreenAdapterUtil.setUp(this, 360, 640)
        ARouter.init(this)
        Log.d("tag", Settings.Secure.getString(BaseApplication.context.contentResolver, Settings.Secure.ANDROID_ID))
        Log.d("tag",UUID.randomUUID().toString())
    }
}