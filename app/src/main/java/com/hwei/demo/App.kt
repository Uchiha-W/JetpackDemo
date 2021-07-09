package com.hwei.demo

import com.alibaba.android.arouter.launcher.ARouter
import com.hwei.lib_common.BaseApplication
import com.hwei.lib_common.util.ScreenAdapterUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }
        ScreenAdapterUtil.setUp(this,360,640)
        ARouter.init(this)
    }
}