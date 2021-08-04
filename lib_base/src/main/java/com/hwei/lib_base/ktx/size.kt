package com.hwei.lib_base.ktx

import com.hwei.lib_base.BaseApplication

val Int.px2dp: Int
    get() = (this / BaseApplication.context.resources.displayMetrics.density + 0.5f).toInt()


val Int.dp2px: Int
    get() = (this * BaseApplication.context.resources.displayMetrics.density + 0.5f).toInt()