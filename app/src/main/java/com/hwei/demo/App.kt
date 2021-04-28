package com.hwei.demo

import com.alibaba.android.arouter.launcher.ARouter
import com.hwei.lib_common.BaseApplication

class App : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
    }
}