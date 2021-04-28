package com.hwei.lib_common.util

import android.app.Activity

object ActivityUtil {
    private val activityList = mutableListOf<Activity>()

    fun add(activity: Activity) {
        activityList.add(activity)
    }

    fun remove(activity: Activity) {
        activityList.remove(activity)
    }
}